package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("policy")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PolicyService {
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
    public Response getPolicyByAuthenticated() {
        var username = securityContext.getUserPrincipal().getName();
        List<PolicyDTO> policyList = PolicyDTO.toDTOs(policyBean.getPolicyByUsername(username));
        if (policyList.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(policyList).build();

    }

    @GET
    @Authenticated
    @Path("/client/{id}")
    public Response getPolicyByAuthenticatedById(@PathParam("id") Long id) {
        var username = securityContext.getUserPrincipal().getName();
        PolicyDTO policyDTO = PolicyDTO.toDTO(policyBean.getPolicyByUsernameDetail(username,id));

        return Response.ok(policyDTO).build();

    }
}
