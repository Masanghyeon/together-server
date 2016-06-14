package kr.co.nexters.together.protocol.dto.response;

import kr.co.nexters.together.protocol.dto.UserDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class AccessTokenResult {
    private String token;
    private UserDTO user;

    public String getToken() {
        return token;
    }

    public AccessTokenResult setToken(String token) {
        this.token = token;
        return this;
    }

    public UserDTO getUser() {
        return user;
    }

    public AccessTokenResult setUser(UserDTO user) {
        this.user = user;
        return this;
    }
}
