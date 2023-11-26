package com.codelab.BytePassword.Service;

import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public interface BytePwdService {

    public JsonObject addUserInfo(BytePwd res);

    public ArrayList<BytePwd> getEmailPwdList();

    public JsonArray getEmailPwdAndEncryptedList();

    @Transactional
    JsonObject deleteEmailPwdCombo(BytePwd bytePwd);
}
