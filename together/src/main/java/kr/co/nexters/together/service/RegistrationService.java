package kr.co.nexters.together.service;

import com.google.inject.Inject;
import kr.co.nexters.together.common.TogetherPreconditions;
import kr.co.nexters.together.common.config.SecretConfiguration;
import kr.co.nexters.together.common.config.TogetherConfiguration;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.common.util.PasswordUtils;
import kr.co.nexters.together.protocol.FacebookStatus;
import kr.co.nexters.together.protocol.dto.UserDTO;
import kr.co.nexters.together.protocol.dto.request.SignUpRequest;
import kr.co.nexters.together.protocol.dto.response.SignUpResult;
import kr.co.nexters.together.protocol.models.EncryptionType;
import kr.co.nexters.together.protocol.models.MUser;
import kr.co.nexters.together.service.mapper.UserMapper;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class RegistrationService extends AbstractService {
    private TogetherConfiguration togetherConfiguration;
    private AccessTokenService accessTokenService;

    @Inject
    public RegistrationService(TogetherConfiguration togetherConfiguration,
                               AccessTokenService accessTokenService) {
        this.togetherConfiguration = togetherConfiguration;
        this.accessTokenService = accessTokenService;
    }

    public SignUpResult signUp(SignUpRequest signUpRequest) throws Exception {
        Session session = beginTransaction();
        MUser user = (MUser)session.createCriteria(MUser.class).add(Restrictions.eq("email", signUpRequest.getEmail())).uniqueResult();
        TogetherPreconditions.checkDuplicated(user == null);
        user = new MUser();
        user.setEmail(signUpRequest.getEmail());
        user.setAge(0);
        SecretConfiguration secretConfiguration = togetherConfiguration.getPassword();
        user.setPassword(PasswordUtils.encryptPassword(secretConfiguration.getSecretKey().getBytes(), signUpRequest.getPassword(), EncryptionType.PBKDF2));
        user.setFacebookStatus(FacebookStatus.NONE);
        user.setCreatedAt(new DateTime(DateTimeZone.UTC).getMillis());
        session.save(user);

        commitAndClose(session);

        UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
        return new SignUpResult(userDTO, accessTokenService.issue(user).getToken());
    }
}
