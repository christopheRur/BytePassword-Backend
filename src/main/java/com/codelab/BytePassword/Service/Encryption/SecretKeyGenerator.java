package com.codelab.BytePassword.Service.Encryption;

import com.codelab.BytePassword.Utils.ToolBox;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class SecretKeyGenerator {
   public static SecretKey generateSecretKey(){

       try {

           KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

           keyGenerator.init(256);

           return keyGenerator.generateKey();

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }

   }

   public static String  getEncodedSecretKey(){

       SecretKey secretKey = generateSecretKey();
       if (secretKey != null){
           byte[] encodedKey=secretKey.getEncoded();

           return ToolBox.byteToHex(encodedKey);
       }
       return null;
   }
}
