package com.codelab.BytePassword.Utils;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class ToolBox {
    /**
     * Helper method to convert bytes to hexadecimal string
     *
     * @param bytes the bytes to convert
     * @return
     */
    public static String byteToHex(byte[] bytes) {

        StringBuilder strRes = new StringBuilder();

        for (byte bit : bytes) {
            strRes.append(String.format("%02X", bit));
        }

        return strRes.toString();
    }

    /**
     * Logs current time
     *
     * @return
     */
    public static String stampTimeOfLogs() {

        LocalDateTime now = LocalDateTime.now();

        String timestamp = String.valueOf(now);
        log.info("Timestamp: " + timestamp);

        return timestamp;
    }

    /**
     *  Return login response.
     * @param message String
     * @param login Boolean
     * @return
     */
    public static JsonObject loginResponse(String message, boolean login){

        JsonObject response = new JsonObject();
        response.addProperty("login", login);
        response.addProperty("message", message);

        return response;

    }

    /**
     * Check if both strings are equal
     *
     * @param dbPassword
     * @param userPassword
     * @return
     */
    public static boolean matches(String dbPassword, String userPassword) {
        return dbPassword.equals(userPassword);
    }

}
