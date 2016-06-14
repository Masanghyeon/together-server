package kr.co.nexters.together.common.exception;

import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.protocol.dto.ErrorDTO;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TogetherExceptionMapper implements ExceptionMapper<TogetherException> {
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(TogetherException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCause(e.getErrorCause());
        errorDTO.setMessage(e.getMessage());
        TogetherResponse<?> togetherResponse = TogetherResponses.error(errorDTO);
        return Response.status(e.getHttpStatus()).entity(togetherResponse).build();
    }
}
