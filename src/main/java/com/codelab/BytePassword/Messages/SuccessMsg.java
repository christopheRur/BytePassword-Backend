package com.codelab.BytePassword.Messages;

import com.google.gson.JsonObject;

public class SuccessMsg {
    public static JsonObject successMessage(String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Message: ",message);
        return jsonObject;
    }
}
