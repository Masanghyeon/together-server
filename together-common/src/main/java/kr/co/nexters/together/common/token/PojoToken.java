package kr.co.nexters.together.common.token;

import java.util.Map;

public interface PojoToken {
    Map<String, Object> serialize();

    void deseialize(Map<String, Object> token);
}
