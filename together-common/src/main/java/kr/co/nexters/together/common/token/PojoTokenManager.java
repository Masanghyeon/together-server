package kr.co.nexters.together.common.token;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PojoTokenManager<T extends PojoToken> implements TokenManager<T> {
    private static final JWTSigner.Options DEFAULT_OPTIONS;

    static {
        DEFAULT_OPTIONS = new JWTSigner.Options();
        DEFAULT_OPTIONS.setJwtId(false);
        DEFAULT_OPTIONS.setIssuedAt(true);
        DEFAULT_OPTIONS.setExpirySeconds((int)(TimeUnit.DAYS.toSeconds(30)));
    }

    private final JWTSigner signer;
    private final JWTVerifier verifier;
    private final JWTSigner.Options options;
    private final FastConstructor fastConstructor;

    public PojoTokenManager(byte[] secret, Class<T> clazz) {
        this(secret, clazz, DEFAULT_OPTIONS);
    }

    public PojoTokenManager(byte[] secret, Class<T> clazz, JWTSigner.Options options) {
        this.signer = new JWTSigner(secret);
        this.verifier = new JWTVerifier(secret);
        this.options = options;
        this.fastConstructor = FastClass.create(clazz).getConstructor(new Class[]{});
    }

    public String sign(T content, int expirySeconds) {
        Map<String, Object> payload = content.serialize();
        JWTSigner.Options newOptions = new JWTSigner.Options();
        newOptions.setExpirySeconds(expirySeconds);
        newOptions.setAlgorithm(options.getAlgorithm());
        newOptions.setIssuedAt(options.isIssuedAt());
        newOptions.setJwtId(options.isJwtId());
        return signer.sign(payload, newOptions);
    }

    public T verify(String token) throws Exception {
        Map<String, Object> payload = verifier.verify(token);
        T content = (T) fastConstructor.newInstance();
        content.deseialize(payload);
        return content;
    }
}
