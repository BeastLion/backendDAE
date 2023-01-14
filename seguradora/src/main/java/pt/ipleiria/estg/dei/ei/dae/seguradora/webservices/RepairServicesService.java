package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.RepairServiceDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.InsurerBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Authenticated
@Path("repair") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class RepairServicesService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private InsurerBean insurerBean;

    @EJB
    private UserBean userBean;

    @GET
    @Authenticated
    @RolesAllowed({"Client"})
    @Path("/{id}/insurer/{insuranceType}")
    public Response getRepairServicesByInsurerOwner(@PathParam("id") int id,@PathParam("insuranceType") String insuranceType) {
        var username = securityContext.getUserPrincipal().getName();
        List<RepairServiceDTO> repairServicesList = RepairServiceDTO.toDTOs(insurerBean.getRepaiServicesByInsurerOwner(id,insuranceType));
        if (repairServicesList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(repairServicesList).build();
    }
}
