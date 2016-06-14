package kr.co.nexters.together.common.exception;

import kr.co.nexters.together.protocol.ErrorCause;

import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

public class TogetherExceptions {

    public static TogetherException create(ErrorCause errorCause, String message, Object... args) {
        return create(Response.Status.INTERNAL_SERVER_ERROR, errorCause, message, args);
    }

    public static TogetherException create(Response.Status status, ErrorCause errorCause, String message, Object... args) {
        TogetherException exception = new TogetherException(format(message, args));
        exception.setHttpStatus(status);
        exception.setErrorCause(errorCause);
        return exception;
    }

    public static TogetherException badRequest(ErrorCause errorCause, String message) {
        TogetherException exception = new TogetherException(message);
        exception.setHttpStatus(Response.Status.BAD_REQUEST);
        exception.setErrorCause(errorCause);
        return exception;
    }

    public static TogetherException forbidden(ErrorCause errorCause, String message) {
        TogetherException exception = new TogetherException(message);
        exception.setHttpStatus(Response.Status.FORBIDDEN);
        exception.setErrorCause(errorCause);
        return exception;
    }

    public static TogetherException forbidden(ErrorCause errorCause, String message, Object... args) {
        TogetherException exception = new TogetherException(format(message, args));
        exception.setHttpStatus(Response.Status.FORBIDDEN);
        exception.setErrorCause(errorCause);
        return exception;
    }

    public static TogetherException notFound(String message) {
        TogetherException exception = new TogetherException(format(message, null));
        exception.setHttpStatus(Response.Status.NOT_FOUND);
        exception.setErrorCause(ErrorCause.NO_SUCH_ELEMENT);
        return exception;
    }

    public static TogetherException notFound(String message, Object... args) {
        TogetherException exception = new TogetherException(format(message, args));
        exception.setHttpStatus(Response.Status.NOT_FOUND);
        exception.setErrorCause(ErrorCause.NO_SUCH_ELEMENT);
        return exception;
    }

    static String format(String template, @Nullable Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
