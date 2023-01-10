package pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.mappers;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    private static final Logger logger =
            Logger.getLogger(NotAuthorizedException.class.getCanonicalName());

    @Override
    public Response toResponse(NotAuthorizedException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorMsg).build();
    }
}