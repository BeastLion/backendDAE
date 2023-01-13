package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("user")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class UserService {
    @EJB
    private UserBean userBean;
    @EJB
    private ClientBean clientBean;
    @EJB
    private PolicyBean policyBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Authenticated
    @Path("/client")
    public Response getUser() {
        var username = securityContext.getUserPrincipal().getName();
        List<Policy> policyList = policyBean.getPolicyByUsername(username);

        if (policyList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(policyList).build();

    }

}