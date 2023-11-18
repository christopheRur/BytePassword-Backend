package com.codelab.BytePassword.Utils;

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
}
