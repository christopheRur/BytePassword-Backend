package com.codelab.BytePassword.Service;

import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BytePwdService {

    public JsonObject addUserInfo(JsonObject res);

    public ArrayList<BytePwd> getEmailPwdList();

    public JsonObject deleteEmailPwdCombo(BytePwd bytePwd);

}
