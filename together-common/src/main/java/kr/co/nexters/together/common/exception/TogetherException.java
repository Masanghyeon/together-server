package kr.co.nexters.together.common.exception;

import kr.co.nexters.together.protocol.ErrorCause;

import javax.ws.rs.core.Response;

public class TogetherException extends Exception {
    private Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
    private ErrorCause errorCause = ErrorCause.UNKNOWN;

    public TogetherException() {
        super();
    }

    public TogetherException(String message) {
        super(message);
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Response.Status httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorCause getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(ErrorCause errorCause) {
        this.errorCause = errorCause;
    }
}
