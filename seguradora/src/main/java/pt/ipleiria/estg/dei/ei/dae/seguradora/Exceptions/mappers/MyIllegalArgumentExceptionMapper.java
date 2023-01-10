package pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyIllegalArgumentException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyIllegalArgumentExceptionMapper implements ExceptionMapper<MyIllegalArgumentException> {

    private static final Logger logger =
            Logger.getLogger(MyIllegalArgumentException.class.getCanonicalName());

    @Override
    public Response toResponse(MyIllegalArgumentException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMsg)
                .build();
    }

}
