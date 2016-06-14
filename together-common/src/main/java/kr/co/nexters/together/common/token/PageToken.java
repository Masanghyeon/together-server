package kr.co.nexters.together.common.token;

import com.google.common.collect.Maps;

import java.util.Map;

public class PageToken implements PojoToken {
    private Integer position;
    private Long createdAt;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("position", position);
        result.put("createdAt", createdAt);
        return result;
    }

    @Override
    public void deseialize(Map<String, Object> verifiedToken) {
        position = (Integer)verifiedToken.get("position");
        createdAt = Long.parseLong(String.valueOf(verifiedToken.get("createdAt")));
    }

    public Integer getPosition() {
        return position;
    }

    public PageToken setPosition(Integer position) {
        this.position = position;
        return this;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public PageToken setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
