package com.codelab.BytePassword.Service.AppUserCredts;

import com.codelab.BytePassword.model.AppUser;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface UserServices {
    public JsonObject logOut(JsonObject res);

    public JsonObject logIn(JsonObject jsonBody);

    public JsonObject registerUser(JsonObject res);

    public ArrayList<AppUser> getEmailPwdList();

}
