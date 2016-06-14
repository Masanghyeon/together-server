package kr.co.nexters.together.common.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class SecretConfiguration implements Configuration {
    private String secretKey;
    private int expirySeconds;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getExpirySeconds() {
        return expirySeconds;
    }

    public void setExpirySeconds(int expirySeconds) {
        this.expirySeconds = expirySeconds;
    }

    public void verify() throws Exception {
        Preconditions.checkNotNull(secretKey, "secretKey must not be null.");
        Preconditions.checkArgument(expirySeconds >= 0, "expirySeconds must be greater than 0.");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("secretKey", secretKey)
                .add("expirySeconds", expirySeconds)
                .toString();
    }
}
