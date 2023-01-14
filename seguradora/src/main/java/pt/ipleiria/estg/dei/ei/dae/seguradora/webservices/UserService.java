package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.UserDetailDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.LinkedList;
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
    @RolesAllowed({"Client", "Expert", "Technician"})
    @Path("/")
    public Response getUserDetail() throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        var user = UserDetailDTO.toDTO(userBean.findOrFail(username));
        return Response.ok(user).build();
    }

}
