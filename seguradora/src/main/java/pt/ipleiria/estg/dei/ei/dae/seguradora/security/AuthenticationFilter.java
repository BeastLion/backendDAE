package pt.ipleiria.estg.dei.ei.dae.seguradora.security;

import io.jsonwebtoken.Jwts;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.security.Key;
import java.security.Principal;

@Provider
@Authenticated
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @EJB
    private UserBean userBean;
    @Context
    private UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            var header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith("Bearer ")) {
                throw new NotAuthorizedException("Authorization header must be provided");
            }

            // Get token from the HTTP Authorization header
            String token = header.substring("Bearer".length()).trim();
            User user = null;

            user = userBean.findOrFail(getUsername(token));


            User finalUser = user;
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return finalUser::getUsername;
                }

                @Override
                public boolean isUserInRole(String s) {
                    return org.hibernate.Hibernate.getClass(finalUser).getSimpleName().equals(s);
                }

                @Override
                public boolean isSecure() {
                    return uriInfo.getAbsolutePath().toString().startsWith("https");
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });
        } catch (MyEntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUsername(String token) {
        Key key = new SecretKeySpec(TokenIssuer.SECRET_KEY, TokenIssuer.ALGORITHM);
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new NotAuthorizedException("Invalid JWT"); // don't trust the JWT!
        }
    }
}
