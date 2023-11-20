package com.codelab.BytePassword.Service.Encryption;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

import static com.codelab.BytePassword.Constant.Constants.AES_ALGO;
@Slf4j
public class PasswordEncryption {

    private static final String secretKey = SecretKeyGenerator.getEncodedSecretKey();

    /**
     * Encodes the password
     * @param password String
     * @return
     */
    public static String encryptPwd(String password) {
        try {

            assert secretKey != null;
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), AES_ALGO);
            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] encryptedBt = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBt);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to encrypt password {}",e.getLocalizedMessage());
        }
        return null;
    }

    /**
     *
     * Decodes the password
     * @param encryptPwd
     * @return
     */
    public static String decryptPwd(String encryptPwd) {
        try {

            assert secretKey != null;
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), AES_ALGO);
            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptPwd);
            byte[] decryptedBts = cipher.doFinal(decodedBytes);
            return  new String(decryptedBts);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to decrypt password {}",e.getLocalizedMessage());

        }
        return null;
    }
}
