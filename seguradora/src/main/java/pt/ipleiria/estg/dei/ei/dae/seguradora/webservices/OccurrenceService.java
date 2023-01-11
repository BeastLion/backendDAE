package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;


import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Authenticated
@Path("occurrences") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OccurrenceService {

    @Context
    private SecurityContext securityContext;
    @EJB
    private OccurrenceBean occurrenceBean;

    @POST
    @Path("/")
    public Response createNewOccurrence(OccurrenceDTO occurrenceDTO) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        occurrenceBean.create(
                occurrenceDTO.getId(),
                occurrenceDTO.getPolicyNumber(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getOccurrenceDate(),
                occurrenceDTO.getLocation(),
                occurrenceDTO.getType(),
                occurrenceDTO.getItem(),
                occurrenceDTO.getStatus(),
                username
        );
        var occurrence = occurrenceBean.findOrFailOccurrence(occurrenceDTO.getId());

        return Response.status(Response.Status.CREATED).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }
}
