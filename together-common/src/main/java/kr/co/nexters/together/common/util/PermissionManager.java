package kr.co.nexters.together.common.util;

import com.auth0.jwt.JWTVerifyException;
import com.google.common.base.Strings;
import kr.co.nexters.together.common.exception.TogetherException;
import kr.co.nexters.together.common.exception.TogetherExceptions;
import kr.co.nexters.together.common.token.AccessToken;
import kr.co.nexters.together.common.token.TokenManager;
import kr.co.nexters.together.protocol.ErrorCause;
import kr.co.nexters.together.protocol.models.MUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;

public class PermissionManager {
    private SessionFactory sessionFactory;
    private TokenManager<AccessToken> accessTokenManager;

    @Inject
    public PermissionManager(SessionFactory sessionFactory,
                             TokenManager<AccessToken> accessTokenManager) {
        this.sessionFactory = sessionFactory;
        this.accessTokenManager = accessTokenManager;
    }

    public MUser checkPermission(HttpServletRequest request) throws Exception {
        String accessTokenString = AuthorizationHeaders.getAccessToken(request);
        if (Strings.isNullOrEmpty(accessTokenString)) {
            throw TogetherExceptions.forbidden(ErrorCause.INSUFFICIENT_PERMISSION, "No authorization.");
        }
        try {
            AccessToken accessToken = accessTokenManager.verify(accessTokenString);
            Session session = this.sessionFactory.openSession();
            MUser user = session.get(MUser.class, accessToken.getUserId());
            if (user == null) {
                throw TogetherExceptions.badRequest(ErrorCause.INSUFFICIENT_PERMISSION, "User doesn't exist.");
            }
            return user;
        } catch (SignatureException e) {
            throw TogetherExceptions.forbidden(ErrorCause.INVALID_TOKEN, "Signature of the accessToken is not valid.");
        } catch (JWTVerifyException e) {
            throw TogetherExceptions.forbidden(ErrorCause.EXPIRED_TOKEN, "Expired accessToken or verification failed.");
        } catch (IllegalStateException e) {
            throw TogetherExceptions.forbidden(ErrorCause.INVALID_TOKEN, "Invalid token.");
        } catch (TogetherException e) {
            throw e;
        } catch (Exception e) {
            throw TogetherExceptions.forbidden(ErrorCause.INSUFFICIENT_PERMISSION, e.getMessage());
        }
    }

    public MUser checkUser(HttpServletRequest request, long userId) throws Exception {
        String accessTokenString = AuthorizationHeaders.getAccessToken(request);
        if (Strings.isNullOrEmpty(accessTokenString)) {
            throw TogetherExceptions.forbidden(ErrorCause.INSUFFICIENT_PERMISSION, "No authorization.");
        }
        /*
         * NoSuchAlgorithmException: 옵션에서 지정한 알고리즘이 유효하지 않은 경우
         * InvalidKeyException: 옵션에서 지정한 키가 유효하지 않은 경우
         * IllegalStateException: 토큰 자체가 적절한 형태가 아닌 경우
         * IOException: 읽다가 뭔가 문제가 생긴 경우
         * SignatureException: Signature 자체가 적절한 형태가 아닌 경우
         * JWTVerifyException: Signature에 있던 정보나 Expire가 지났거나 하여 유효한 토큰이 아닌 경우
         */
        try {
            AccessToken accessToken = accessTokenManager.verify(accessTokenString);
            Session session = this.sessionFactory.openSession();
            MUser user = session.get(MUser.class, accessToken.getUserId());
            if (user == null) {
                throw TogetherExceptions.badRequest(ErrorCause.INSUFFICIENT_PERMISSION, "User doesn't exist.");
            }
            if (accessToken.getUserId() != userId) {
                throw TogetherExceptions.forbidden(ErrorCause.INSUFFICIENT_PERMISSION, "User is not matched.");
            }
            return user;
        } catch (SignatureException e) {
            throw TogetherExceptions.forbidden(ErrorCause.INVALID_TOKEN, "Signature of the accessToken is not valid.");
        } catch (JWTVerifyException e) {
            throw TogetherExceptions.forbidden(ErrorCause.EXPIRED_TOKEN, "Expired accessToken or verification failed.");
        } catch (IllegalStateException e) {
            throw TogetherExceptions.forbidden(ErrorCause.INVALID_TOKEN, "Invalid token.");
        } catch (TogetherException e) {
            throw e;
        } catch (Exception e) {
            throw TogetherExceptions.forbidden(ErrorCause.INSUFFICIENT_PERMISSION, e.getMessage());
        }
    }
}
