package kr.co.nexters.together.common.token;

import com.google.common.collect.Maps;

import java.util.Map;

public class AccessToken implements PojoToken {
    private Long userId;
    private String email;
    private Long createdAt;

    public Map<String, Object> serialize() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("userId", userId);
        result.put("email", email);
        result.put("createdAt", createdAt);
        return result;
    }

    public void deseialize(Map<String, Object> verifiedToken) {
        userId = Long.parseLong(String.valueOf(verifiedToken.get("userId")));
        email = String.valueOf(verifiedToken.get("email"));
        createdAt = Long.parseLong(String.valueOf(verifiedToken.get("createdAt")));
    }

    public long getUserId() {
        return userId;
    }

    public AccessToken setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccessToken setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public AccessToken setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
