package com.codelab.BytePassword.Service.AppUserCredts;

import com.google.gson.JsonObject;

public interface UserServices {
    public JsonObject logOut(JsonObject res);
    public JsonObject logIn(JsonObject jsonBody);

    public JsonObject registerUser(JsonObject res);
}
