package com.codelab.BytePassword.Service.Encryption;

import com.codelab.BytePassword.Utils.ToolBox;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import static com.codelab.BytePassword.Constant.Constants.AES_ALGO;

public class SecretKeyGenerator {
    public static SecretKey generateSecretKey() {

        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGO);

            keyGenerator.init(128);

            return keyGenerator.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static String getEncodedSecretKey() {

        SecretKey secretKey = generateSecretKey();
        if (secretKey != null) {
            byte[] encodedKey = secretKey.getEncoded();

            return ToolBox.byteToHex(encodedKey);
        }
        return null;
    }
}
