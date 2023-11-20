package com.codelab.BytePassword.Messages;

import com.google.gson.JsonObject;

public class ErrorMsg {
    public static JsonObject errorMessage(String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Message: ",message);
        return jsonObject;
    }
}
