package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.TokenIssuer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO authDTO) {
        System.out.println("----------------------------------");
        System.out.println("--------------AQUI----------------");
        System.out.println("----------------------------------");
        if (userBean.canLogin(authDTO.getUsername(), authDTO.getPassword())) {
            System.out.println("----------------------------------");
            System.out.println("--------------AQUI2----------------");
            System.out.println("----------------------------------");
            String token = issuer.issue(authDTO.getUsername());
            System.out.println("----------------------------------");
            System.out.println("--------------AQUI3----------------");
            System.out.println("----------------------------------");
            return Response.ok(token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
