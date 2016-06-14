package kr.co.nexters.together.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.co.nexters.together.protocol.ResponseStatus;
import kr.co.nexters.together.protocol.dto.ErrorDTO;
import kr.co.nexters.together.protocol.dto.PagingDTO;

public class TogetherResponse<T> {
    private final ResponseStatus status;
    private final T result;
    private final PagingDTO paging;
    private final ErrorDTO error;

    @JsonCreator
    public TogetherResponse(ResponseStatus status,
                            T result,
                            PagingDTO paging,
                            ErrorDTO error) {
        this.status = status;
        this.result = result;
        this.paging = paging;
        this.error = error;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public T getResult() {
        return result;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public ErrorDTO getError() {
        return error;
    }
}
