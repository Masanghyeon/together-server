package kr.co.nexters.together.protocol.dto;

import kr.co.nexters.together.protocol.CharacterType;
import kr.co.nexters.together.protocol.FacebookStatus;
import kr.co.nexters.together.protocol.Gender;
import kr.co.nexters.together.protocol.TravelPreference;
import kr.co.nexters.together.protocol.dto.ProfilePhotoDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class CompactUserDTO {
    private long id;
    private String nickname;
    private Gender gender;
    private int age;
    private String description;
    private CharacterType characterType;
    private List<TravelPreference> travelPreferences;
    private FacebookStatus facebookStatus;
    private ProfilePhotoDTO profilePhotoDTO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public List<TravelPreference> getTravelPreferences() {
        return travelPreferences;
    }

    public void setTravelPreferences(List<TravelPreference> travelPreferences) {
        this.travelPreferences = travelPreferences;
    }

    public FacebookStatus getFacebookStatus() {
        return facebookStatus;
    }

    public void setFacebookStatus(FacebookStatus facebookStatus) {
        this.facebookStatus = facebookStatus;
    }

    public ProfilePhotoDTO getProfilePhotoDTO() {
        return profilePhotoDTO;
    }

    public void setProfilePhotoDTO(ProfilePhotoDTO profilePhotoDTO) {
        this.profilePhotoDTO = profilePhotoDTO;
    }
}
