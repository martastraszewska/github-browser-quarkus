package pl.straszewska.githubbrowserquarkus.error;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pl.straszewska.githubbrowserquarkus.api.dtos.ErrorDto;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof GithubUserNotFoundException) {
            Log.info(exception.getMessage());
            return Response
                    .status(BAD_REQUEST).entity(new ErrorDto("404", exception.getMessage()))
                    .build();
        } else {
            Log.error("Unexpected error", exception);
            return Response
                    .status(INTERNAL_SERVER_ERROR).entity(new ErrorDto("500", "Something went wrong"))
                    .build();
        }
    }
}
