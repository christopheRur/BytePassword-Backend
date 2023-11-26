package com.codelab.BytePassword.Service.Encryption;

import com.codelab.BytePassword.Utils.ToolBox;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import static com.codelab.BytePassword.Constant.Constants.*;

@Slf4j
public class PasswordEncryption {

    /**
     * Encrypts the password
     *
     * @param inputPassword String
     * @return
     */
    public static String encryptPassword(String inputPassword) {
        try {
            StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
            log.info("->Password ENCRYPTION IN PROGRESS...");
            return encryptor.encryptPassword(inputPassword);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to Encrypt password {}", e.getLocalizedMessage());
        }
        return null;
    }


    /**
     * Validate the password; check the unencrypted password against the encrypted password
     *
     * @param password
     * @param encryptedStoredPassword
     * @return
     */
    public static boolean validatePassword(String password, String encryptedStoredPassword) {
        try {
            StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
            log.info("->Validating password...");
            return encryptor.checkPassword(password, encryptedStoredPassword);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to VALIDATE password {}", e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }

    /**
     * Encrypts the managed passwords to be stored in the database.
     *
     * @param password String
     * @return String
     */
    public static String encryptDataPassword(String password) {

        try {
            byte[] salt = generateSalt();
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SHA_254);
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), AES_ALGO);
            Cipher cipher = Cipher.getInstance(AES_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            String saltStr = Base64.getEncoder().encodeToString(salt);
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);

            return saltStr + "$" + encryptedPassword;

        } catch (Exception e) {

            e.printStackTrace();

            log.error("Failed to encryptDataPassword password {}", e.getLocalizedMessage());
        }

        return null;
    }

    /**
     * Verifies if the provided password matches the stored encrypted password.
     *
     * @param password       The password to be verified.
     * @param storedPassword The stored encrypted password.
     * @return True if the password matches, otherwise false.
     */
    public static JsonObject verifyDecryptedDataPassword(String password, String storedPassword) {
        try {

            String[] parts = storedPassword.split("\\$");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] encryptedPassword = Base64.getDecoder().decode(parts[1]);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(SHA_254);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(AES_ECB);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedPassword);
            String decryptedPassword = new String(decryptedBytes);

            JsonObject result = new JsonObject();

            if (ToolBox.matches(decryptedPassword, password)) {

                result.addProperty("Decryption", Boolean.TRUE);
                result.addProperty("Success", "Encryption and Decryption succeeded");
                result.addProperty("DecryptedPassword", decryptedPassword);
                result.addProperty("EncryptedPassword", encryptPassword(password));

                return result;
            }

        } catch (Exception e) {

            e.printStackTrace();

            log.error("Failed to decryptDataPassword password {}", e.getLocalizedMessage());
        }


        return null;
    }


    /**
     * Generates salt
     *
     * @return byte[]
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


}
