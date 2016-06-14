package kr.co.nexters.together.common.util;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

public final class AuthorizationHeaders {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ACCESS_TOKEN_PREFIX = "access_token=";
    public static final String UPLOAD_TOKEN_PREFIX = "upload_token=";

    public static String getToken(HttpServletRequest request, String prefix) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (Strings.isNullOrEmpty(authorizationHeader)) {
            return null;
        }
        authorizationHeader = authorizationHeader.trim();
        if (!authorizationHeader.startsWith(prefix)) {
            return null;
        }
        return authorizationHeader.substring(prefix.length());
    }

    public static String getAccessToken(HttpServletRequest request) {
        return getToken(request, ACCESS_TOKEN_PREFIX);
    }

    public static String getUploadToken(HttpServletRequest request) {
        return getToken(request, UPLOAD_TOKEN_PREFIX);
    }
}
