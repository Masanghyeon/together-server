package kr.co.nexters.together.common.util;

import kr.co.nexters.together.protocol.models.EncryptionType;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class PasswordUtils {
    private PasswordUtils() {}

//    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class);

    // 최소 6자에서 최대 256자 알파벳(소문자,대문) + 특수문자(키보드만으로 만들수 있는 모든 특수문자.)
    private static final Pattern PATTERN = Pattern.compile("^[\\p{Graph}]{6,256}$");

    private static final int ITERATION_COUNT = 4096;
    private static final int DERIVED_KEY_LENGTH = 256;
    private static SecretKeyFactory SKF = null;
    static {
        try {
            SKF = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
//            LOGGER.error(e.getMessage(), e);
        }
    }

    public static boolean isValidPasswordFormat(String password) {
        return PATTERN.matcher(password).matches();
    }

    private static String encryptPasswordByPbkdf2(byte[] salt, String password) throws Exception {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, DERIVED_KEY_LENGTH);
            return Base64.encodeBase64String(SKF.generateSecret(spec).getEncoded());
        } catch (InvalidKeySpecException e) {
            throw new Exception(e);
        }
    }

    public static String encryptPassword(byte[] salt, String password, EncryptionType encryptionType) throws Exception {
        switch (encryptionType) {
            case PBKDF2:
            default:
                return PasswordUtils.encryptPasswordByPbkdf2(salt, password);
        }
    }
}
