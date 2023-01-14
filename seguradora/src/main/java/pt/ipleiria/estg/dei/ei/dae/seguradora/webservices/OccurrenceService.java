package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.RepairServiceDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
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
@Path("occurrences") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class OccurrenceService {

    @Context
    private SecurityContext securityContext;
    @EJB
    private OccurrenceBean occurrenceBean;
    @EJB
    private UserBean userBean;

    @POST
    @RolesAllowed({"Client"})
    @Path("/")
    public Response createNewOccurrence(OccurrenceDTO occurrenceDTO) throws MyEntityNotFoundException, MyEntityExistsException.MyConstraintViolationException {
        var username = securityContext.getUserPrincipal().getName();
        Long id = occurrenceBean.create(
                occurrenceDTO.getPolicyNumber(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getLocation(),
                occurrenceDTO.getType(),
                occurrenceDTO.getItem(),
                username
        );
        var occurrence = occurrenceBean.findOrFailOccurrence(id);
        return Response.status(Response.Status.CREATED).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }

    @PUT
    @RolesAllowed({"Client", "Expert"})
    @Path("/{id}")
    public Response updateOccurrence(@PathParam("id") Long id, OccurrenceDTO occurrenceDTO) throws MyEntityNotFoundException {
        if(occurrenceBean.findOccurrenceisDeleted(id)){
            return Response.status(Response.Status.NO_CONTENT).entity("Occurrence is deleted").build();
        }

        occurrenceBean.update(id, occurrenceDTO.getDescription(), occurrenceDTO.getLocation(), occurrenceDTO.getType(), occurrenceDTO.getItem());

        //TODO temos de validar se o USER tem essa ocurrencia ou seja logo tem que se mudar ocurrencia de sitio, list<Occurrrencia> vai para user

        //TODO nao esquecer lazy loads !!
        var occurrence = occurrenceBean.findOrFailOccurrence(id);

        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }

    @GET
    @RolesAllowed({"Client", "Expert"})
    @Path("/{id}")
    public Response getOccurrenceDetails(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        var client = userBean.findOrFail(username);

        if(occurrenceBean.findOccurrenceisDeleted(id)){
            return Response.status(Response.Status.NO_CONTENT).entity("Occurrence is deleted").build();
        }

        var occurrence = occurrenceBean.findOrFailOccurrence(id);
        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }

    @DELETE
    @RolesAllowed({"Client", "Expert"})
    @Path("/{id}")
    public Response removeOccurrence(@PathParam("id") Long id) throws MyEntityNotFoundException {
        occurrenceBean.remove(id);

        if (!occurrenceBean.findOccurrenceisDeleted(id)) {
            return Response.status(Response.Status.NOT_MODIFIED).entity("Occurrence not deleted").build();
        }

        return Response.status(Response.Status.OK).entity("Occurence deleted with success").build();
    }

    @GET
    @RolesAllowed({"Client", "Expert","Technician"})
    @Path("/")
    public Response getAllOccurrences() throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        List<Occurrence> occurrences = userBean.getOcccurrenceByUser(username);
        if(occurrences == null){
          return Response.status(Response.Status.NO_CONTENT).entity("Theres no list of occurrences available").build();
        }
        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTOs(occurrences)).build();
    }

    @GET
    @RolesAllowed({"Expert"})
    @Path("/expert")
    public Response getAllOccurrencesAvailable() throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        List<Occurrence> occurrences = occurrenceBean.findAvailableForExpert(username);
        if(occurrences == null){
            return Response.status(Response.Status.NO_CONTENT).entity("Theres no list of occurrences available").build();
        }
        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTOs(occurrences)).build();
    }

    @POST
    @RolesAllowed({"Expert"})
    @Path("/enroll/{id}")
    public Response EnrollExpertOccurrence(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        var user = UserDTO.from(userBean.findOrFail(username));

        if(occurrenceBean.findOccurrenceisDeleted(id)){
            return Response.status(Response.Status.NO_CONTENT).entity("Occurrence is deleted").build();
        }

        occurrenceBean.enrollExpertOccurrence(username,id);

        //falta verificar se ele ficou la

        return Response.status(Response.Status.OK).entity(user).build();
    }

    @POST
    @RolesAllowed({"Expert"})
    @Path("/unroll/{id}")
    public Response UnrollExpertOccurrence(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();

        if(occurrenceBean.findOccurrenceisDeleted(id)){
            return Response.status(Response.Status.NO_CONTENT).entity("Occurrence is deleted").build();
        }

        occurrenceBean.unrollExpertOccurrence(username,id);

        //falta verificar se ele ficou la

        return Response.status(Response.Status.OK).entity("Expert  username:" + username + "unrolled to occurrence id"  + id + " with success").build();
    }

    @PUT
    @RolesAllowed({"Expert","Technician"})
    @Path("/status/{id}")
    public Response changeStatus(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();

        if(occurrenceBean.findOccurrenceisDeleted(id)){
            return Response.status(Response.Status.NO_CONTENT).entity("Occurrence is deleted").build();
        }

        boolean isChanged = occurrenceBean.changeStatus(id, username);

        if(!isChanged){
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @RolesAllowed({"Client"})
    @Path("/status/{id}/technician/{idRepaiService}")
    public Response assignTechnician(@PathParam("id") Long id,@PathParam("idRepaiService") Long idRepaiService) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();

        if(occurrenceBean.findOccurrenceisDeleted(id)){
            return Response.status(Response.Status.NO_CONTENT).entity("Occurrence is deleted").build();
        }

        boolean isChanged = occurrenceBean.assignTechnician(id, username,idRepaiService);

        if(!isChanged){
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
