package kr.co.nexters.together.common.token;

public interface TokenManager<T> {
    /***
     * 주어진 content를 이용하여 token을 만든다
     * @param content
     * @param expirySeconds
     * @return
     */
    String sign(T content, int expirySeconds);

    /***
     * token을 받아 주어진 content를 반환한다.
     * @param token
     * @return
     * @throws Exception
     */
    T verify(String token) throws Exception;
}
