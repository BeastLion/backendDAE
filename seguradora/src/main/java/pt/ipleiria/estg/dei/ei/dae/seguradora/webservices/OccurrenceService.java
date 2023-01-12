package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;

import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

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

    @POST
    @Path("/")
    public Response createNewOccurrence(OccurrenceDTO occurrenceDTO) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        occurrenceBean.create(
                occurrenceDTO.getPolicyNumber(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getLocation(),
                occurrenceDTO.getType(),
                occurrenceDTO.getItem(),
                username
        );

        //TODO nao esquecer lazy loads !!
        //var occurrence = occurrenceBean.findOrFailOccurrence(occurrenceDTO.getId());

        return Response.status(Response.Status.CREATED).entity("OccurrenceDTO.toDTO(occurrence)").build();
        //return Response.status(Response.Status.CREATED).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOccurrence(@PathParam("id") Long id, OccurrenceDTO occurrenceDTO) throws MyEntityNotFoundException {

        occurrenceBean.update(id, occurrenceDTO.getDescription(), occurrenceDTO.getLocation(), occurrenceDTO.getType(), occurrenceDTO.getItem(), occurrenceDTO.getUsers());

        //TODO temos de validar se o USER tem essa ocurrencia ou seja logo tem que se mudar ocurrencia de sitio, list<Occurrrencia> vai para user

        //TODO nao esquecer lazy loads !!
        var occurrence = occurrenceBean.findOrFailOccurrence(id);

        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }

    @GET
    @Path("/{id}")
    public Response getOccurrenceDetails(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        var client = occurrenceBean.findOrFailClient(username);

        //TODO temos de validar se o USER tem essa ocurrencia ou seja logo tem que se mudar ocurrencia de sitio, list<Occurrrencia> vai para user

        /*
        if(!idDaOccurenciaDoUser.equals(id)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        */

        //TODO nao esquecer lazy loads !!
        var occurrence = occurrenceBean.findOrFailOccurrence(id);
        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTO(occurrence)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeOccurrency(@PathParam("id") Long id) throws MyEntityNotFoundException {

        occurrenceBean.remove(id);

        if (occurrenceBean.findOrFailOccurrenceForDelete(id) != null) {
            return Response.status(Response.Status.NOT_MODIFIED).entity("Occurrence not deleted").build();
        }

        return Response.status(Response.Status.OK).entity("Occurence deleted with success").build();
    }

    @GET
    @Path("/")
    public Response getAllOccurrences() throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        List<Occurrence> occurrences = occurrenceBean.findOccurrenceByUsername(username);
        if(occurrences == null){
          return Response.status(Response.Status.NO_CONTENT).entity("Theres no list of occurrences available").build();
        }
        return Response.status(Response.Status.OK).entity(OccurrenceDTO.toDTOs(occurrences)).build();
    }
}
