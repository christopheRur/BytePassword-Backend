package com.codelab.BytePassword.Utils;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
public class ToolBox {
    /**
     * Helper method to convert bytes to hexadecimal string
     * @param bytes the bytes to convert
     * @return
     */
    public static String byteToHex(byte[] bytes) {

        StringBuilder strRes = new StringBuilder();

        for(byte bit: bytes){
            strRes.append(String.format("%02X",bit));
        }

        return strRes.toString();
    }

    public static String stampTimeOfLogs(){

        LocalDateTime now = LocalDateTime.now();

        String timestamp = String.valueOf(now);
        log.info("Timestamp:-=------------------------------------- " + timestamp);

        return timestamp;
    }
}
