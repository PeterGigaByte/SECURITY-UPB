import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltHashing {
    public SaltHashing() {
    }
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

    public static String bytetoString(byte[] input) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(input);
    }

    public static byte[] getHashWithSalt(String input, byte[] salt) throws NoSuchAlgorithmException {
        String hashing = "SHA-256";
        MessageDigest digest = MessageDigest.getInstance(hashing);
        digest.reset();
        digest.update(salt);
        return digest.digest(stringToByte(input));
    }
    public static byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

}
