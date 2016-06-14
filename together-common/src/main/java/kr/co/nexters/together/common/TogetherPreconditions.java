package kr.co.nexters.together.common;

import kr.co.nexters.together.common.exception.TogetherExceptions;
import kr.co.nexters.together.protocol.ErrorCause;

import javax.ws.rs.core.Response;

public final class TogetherPreconditions {

    public static void checkArgument(boolean expression, String message) throws Exception {
        checkArgument(expression, ErrorCause.INVALID_ARGUMENT, message);
    }

    public static void checkArgument(boolean expression, ErrorCause errorCause, String message) throws Exception {
        checkArgument(expression, Response.Status.BAD_REQUEST, errorCause, message);
    }

    public static void checkArgument(boolean expression, Response.Status status, ErrorCause errorCause, String message) throws Exception {
        if (!expression) {
            throw TogetherExceptions.create(status, errorCause, message);
        }
    }

    public static void checkNotFound(boolean expression) throws Exception {
        checkArgument(expression, ErrorCause.NO_SUCH_ELEMENT, "Couldn't found the element.");
    }

    public static void checkDuplicated(boolean expression) throws Exception {
        checkArgument(expression, ErrorCause.ELEMENT_ALREADY_EXISTS, "Already exists.");
    }
}
