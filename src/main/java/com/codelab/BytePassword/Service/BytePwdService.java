package com.codelab.BytePassword.Service;

import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import javafx.util.Pair;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public interface BytePwdService {

    public JsonObject addUserInfo(BytePwd res);

    public ArrayList<BytePwd> getEmailPwdList();
    public List<Pair<String, String>> getEmailAndDecryptedPwdList();

    @Transactional
    JsonObject deleteEmailPwdCombo(BytePwd bytePwd);
}
