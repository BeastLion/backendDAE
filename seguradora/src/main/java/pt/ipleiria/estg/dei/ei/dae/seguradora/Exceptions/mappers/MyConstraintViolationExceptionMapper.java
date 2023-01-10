package pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.mappers;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyConstraintViolationExceptionMapper implements ExceptionMapper<MyConstraintViolationException> {
    private static final Logger logger =
            Logger.getLogger(MyConstraintViolationException.class.getCanonicalName());

    @Override
    public Response toResponse(MyConstraintViolationException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMsg)
                .build();
    }
}
