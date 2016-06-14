package kr.co.nexters.together.protocol.dto.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class ProfilePhotoPostRequest {
    private String photoToken;

    public String getPhotoToken() {
        return photoToken;
    }

    public void setPhotoToken(String photoToken) {
        this.photoToken = photoToken;
    }
}
