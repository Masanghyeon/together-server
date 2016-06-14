package kr.co.nexters.together.common.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.io.File;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class TogetherConfiguration implements Configuration {
    public static TogetherConfiguration populate(File file) throws Exception {
        TogetherConfiguration conf = new ObjectMapper().readValue(file, TogetherConfiguration.class);
        conf.verify();
        return conf;
    }

    private int port;
    private SecretConfiguration password;
    private SecretConfiguration accessToken;
    private SecretConfiguration uploadToken;
    private SecretConfiguration pageToken;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SecretConfiguration getPassword() {
        return password;
    }

    public void setPassword(SecretConfiguration password) {
        this.password = password;
    }

    public SecretConfiguration getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(SecretConfiguration accessToken) {
        this.accessToken = accessToken;
    }

    public SecretConfiguration getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(SecretConfiguration uploadToken) {
        this.uploadToken = uploadToken;
    }

    public SecretConfiguration getPageToken() {
        return pageToken;
    }

    public void setPageToken(SecretConfiguration pageToken) {
        this.pageToken = pageToken;
    }

    public void verify() throws Exception {
        Preconditions.checkArgument(1 <= port && port <= 65535, "port must be within 1~66535");
        accessToken.verify();
        uploadToken.verify();
        pageToken.verify();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("port", port)
                .add("password", password)
                .add("accessToken", accessToken)
                .add("uploadToken", uploadToken)
                .toString();
    }
}
