package kr.co.nexters.together.common;

import kr.co.nexters.together.protocol.ResponseStatus;
import kr.co.nexters.together.protocol.dto.ErrorDTO;
import kr.co.nexters.together.protocol.dto.PagingDTO;

import java.util.List;

public class TogetherResponses {
    private TogetherResponses() {}

    public static <T> TogetherResponse<T> success(T result) {
        return new TogetherResponse<>(ResponseStatus.SUCCESS, result, null, null);
    }

    public static <T> TogetherResponse<PagingList<T>> success(PagingList<T> result) {
        return new TogetherResponse<>(ResponseStatus.SUCCESS, result, result.getPaging(), null);
    }

    public static <T> TogetherResponse<T> error(ErrorDTO error) {
        return new TogetherResponse<>(ResponseStatus.ERROR, null, null, error);
    }
}
