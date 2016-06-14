package kr.co.nexters.together.protocol.dto.response;

import kr.co.nexters.together.protocol.dto.UserDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class SignUpResult {
    private UserDTO user;
    private String accessToken;

    public SignUpResult(UserDTO user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
