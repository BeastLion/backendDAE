package pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyEntityExistsExceptionMapper implements ExceptionMapper<MyEntityExistsException> {
    private static final Logger logger =
            Logger.getLogger(MyEntityExistsException.class.getCanonicalName());
    @Override
    public Response toResponse(MyEntityExistsException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.CONFLICT)
                .entity(errorMsg)
                .build();
    }
}
