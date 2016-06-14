package kr.co.nexters.together.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import kr.co.nexters.together.common.TogetherPreconditions;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.common.token.TokenManager;
import kr.co.nexters.together.common.token.UploadToken;
import kr.co.nexters.together.protocol.ErrorCause;
import kr.co.nexters.together.protocol.dto.request.ProfilePhotoPostRequest;
import kr.co.nexters.together.protocol.dto.request.UserPatchRequest;
import kr.co.nexters.together.protocol.models.MImage;
import kr.co.nexters.together.protocol.models.MProfilePhoto;
import kr.co.nexters.together.protocol.models.MTravelPreference;
import kr.co.nexters.together.protocol.models.MUser;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Set;

public class UserService extends AbstractService {
    private TokenManager<UploadToken> uploadTokenManager;

    @Inject
    public UserService(TokenManager<UploadToken> uploadTokenManager) {
        this.uploadTokenManager = uploadTokenManager;
    }

    public MUser get(long userId) throws Exception {
        Session session = getSessionFactory().openSession();
        MUser user = session.get(MUser.class, userId);
        session.close();
        return user;
    }

    public MUser patch(long userId, UserPatchRequest patchRequest) throws Exception {
        Session session = beginTransaction();
        MUser user = session.get(MUser.class, userId);
        TogetherPreconditions.checkArgument(patchRequest != null, "patchRequest must be not null.");
        if (!Strings.isNullOrEmpty(patchRequest.getNickname())) {
            user.setNickname(patchRequest.getNickname());
        }
        if (patchRequest.getGender() != null) {
            user.setGender(patchRequest.getGender());
        }
        if (patchRequest.getAge() != null) {
            TogetherPreconditions.checkArgument(patchRequest.getAge() > 0, "Age is wrong.");
            user.setAge(patchRequest.getAge());
        }
        if (patchRequest.getDescription() != null) {
            user.setDescription(patchRequest.getDescription());
        }
        if (patchRequest.getCharacterType() != null) {
            user.setCharacterType(patchRequest.getCharacterType());
        }
        if (patchRequest.getTravelPreferences() != null && !patchRequest.getTravelPreferences().isEmpty()) {
            TogetherPreconditions.checkArgument(patchRequest.getTravelPreferences().size() <= 5, ErrorCause.TRAVEL_PREFERENCE_LIMIT_EXCESS, "Travel preferences count must be smaller than 5.");
            Set<MTravelPreference> travelPreferences = Sets.newHashSet();
            // 이전 travel preference 제거
            user.getTravelPreferences().forEach(mtp -> {
                mtp.getUsers().remove(user);
            });
            // 새로운 travel preference 추가
            patchRequest.getTravelPreferences().forEach(tp -> {
                MTravelPreference mtp = (MTravelPreference)session.createCriteria(MTravelPreference.class).add(Restrictions.eq("value", tp)).uniqueResult();
                travelPreferences.add(mtp);
                mtp.getUsers().add(user);
            });
            user.setTravelPreferences(travelPreferences);
        }
        if (patchRequest.getFacebookStatus() != null) {
            user.setFacebookStatus(patchRequest.getFacebookStatus());
        }
        commitAndClose(session);
        return user;
    }

    public MUser setProfilePhoto(long userId, ProfilePhotoPostRequest profilePhotoPostRequest) throws Exception {
        TogetherPreconditions.checkArgument(profilePhotoPostRequest.getPhotoToken() != null, "Photo token must not be null.");
        UploadToken uploadToken = uploadTokenManager.verify(profilePhotoPostRequest.getPhotoToken());
        List<MImage> images = uploadToken.getImages();

        Session session = beginTransaction();
        MUser user = session.get(MUser.class, userId);
        MProfilePhoto prevProfilePhoto = user.getProfilePhoto();
        if (prevProfilePhoto != null) {
            session.delete(prevProfilePhoto);
        }
        MProfilePhoto newProfilePhoto = new MProfilePhoto();
        newProfilePhoto.setImages(Sets.newHashSet());
        for (MImage image : images) {
            session.save(image);
            newProfilePhoto.getImages().add(image);
        }
        user.setProfilePhoto(newProfilePhoto);
        commitAndClose(session);
        return user;
    }
}
