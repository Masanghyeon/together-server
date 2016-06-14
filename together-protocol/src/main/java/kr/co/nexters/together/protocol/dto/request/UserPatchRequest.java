package kr.co.nexters.together.protocol.dto.request;

import kr.co.nexters.together.protocol.CharacterType;
import kr.co.nexters.together.protocol.FacebookStatus;
import kr.co.nexters.together.protocol.Gender;
import kr.co.nexters.together.protocol.TravelPreference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class UserPatchRequest {
    private String nickname;
    private Gender gender;
    private Integer age;
    private String description;
    private CharacterType characterType;
    private List<TravelPreference> travelPreferences;
    private FacebookStatus facebookStatus;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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
}
