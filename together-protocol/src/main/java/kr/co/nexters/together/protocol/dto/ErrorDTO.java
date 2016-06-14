package kr.co.nexters.together.protocol.dto;

import kr.co.nexters.together.protocol.ErrorCause;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class ErrorDTO {
    private ErrorCause errorCause;
    private String message;

    public ErrorCause getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(ErrorCause errorCause) {
        this.errorCause = errorCause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
