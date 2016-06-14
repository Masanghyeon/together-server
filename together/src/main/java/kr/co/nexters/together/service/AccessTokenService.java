package kr.co.nexters.together.service;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import kr.co.nexters.together.common.TogetherPreconditions;
import kr.co.nexters.together.common.config.TogetherConfiguration;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.common.token.AccessToken;
import kr.co.nexters.together.common.token.TokenManager;
import kr.co.nexters.together.common.util.PasswordUtils;
import kr.co.nexters.together.protocol.ErrorCause;
import kr.co.nexters.together.protocol.dto.UserDTO;
import kr.co.nexters.together.protocol.dto.request.IssueAccessTokenRequest;
import kr.co.nexters.together.protocol.dto.response.AccessTokenResult;
import kr.co.nexters.together.protocol.models.EncryptionType;
import kr.co.nexters.together.protocol.models.MUser;
import kr.co.nexters.together.service.mapper.UserMapper;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class AccessTokenService extends AbstractService {

    private TogetherConfiguration configuration;
    private TokenManager<AccessToken> tokenManager;

    @Inject
    public AccessTokenService(
            TogetherConfiguration configuration,
            TokenManager<AccessToken> tokenManager) {
        this.configuration = configuration;
        this.tokenManager = tokenManager;
    }

    public AccessTokenResult issue(IssueAccessTokenRequest issueAccessTokenRequest) throws Exception {
        TogetherPreconditions.checkArgument(!Strings.isNullOrEmpty(issueAccessTokenRequest.getEmail()), ErrorCause.INVALID_ARGUMENT, "Email must be not null.");
        TogetherPreconditions.checkArgument(!Strings.isNullOrEmpty(issueAccessTokenRequest.getPassword()), ErrorCause.INVALID_ARGUMENT, "Password must be not null.");
        Session session = beginTransaction();
        MUser user = (MUser)session.createCriteria(MUser.class).add(Restrictions.eq("email", issueAccessTokenRequest.getEmail())).uniqueResult();
        session.close();
        TogetherPreconditions.checkArgument(user != null, ErrorCause.NO_SUCH_ELEMENT, "User does not exist.");

        byte[] salt = this.configuration.getPassword().getSecretKey().getBytes();
        String encryptedPassword = PasswordUtils.encryptPassword(salt, issueAccessTokenRequest.getPassword(), EncryptionType.PBKDF2);
        TogetherPreconditions.checkArgument(encryptedPassword.equals(user.getPassword()), ErrorCause.PASSWORD_NOT_MATCH, "Password is not matched.");

        return issue(user);
    }

    public AccessTokenResult issue(MUser user) throws Exception {
        AccessToken accessTokenObject = new AccessToken();
        accessTokenObject.setUserId(user.getId());
        accessTokenObject.setEmail(user.getEmail());
        accessTokenObject.setCreatedAt(new DateTime(DateTimeZone.UTC).getMillis());
        String token = tokenManager.sign(accessTokenObject, configuration.getAccessToken().getExpirySeconds());
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
        return new AccessTokenResult().setToken(token).setUser(userDTO);
    }
}
