package com.codelab.BytePassword.Service.AppUserCredts;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {
    public JsonObject logOut(JsonObject res);
    public JsonObject logIn(JsonObject jsonBody);
    public JsonObject registerUser(JsonObject res);
}
