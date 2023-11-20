package com.codelab.BytePassword.Service;

import com.codelab.BytePassword.Messages.ErrorMsg;
import com.codelab.BytePassword.Messages.SuccessMsg;
import com.codelab.BytePassword.Repository.BytePwdRepository;
import com.codelab.BytePassword.Service.Encryption.PasswordEncryption;
import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class BytePwdServiceImpl implements BytePwdService {

    @Autowired
    private BytePwdRepository byteRep;

    /**
     * Adds user
     * @param res
     * @return
     */
    @Override
    public JsonObject addUserInfo(JsonObject res) {

        String email = String.valueOf(res.get("email"));
        String password = PasswordEncryption.encryptPwd(String.valueOf(res.get("password")));
        String hint = String.valueOf(res.get("hint"));
        String message = String.valueOf(res.get("message"));

        BytePwd bytePwd = new BytePwd();

        bytePwd.setEmail(email);
        bytePwd.setPassword(password);
        bytePwd.setHint(hint);
        bytePwd.setMessage(message);

        if (byteRep.findByEmail(email).isEmpty()) {
            byteRep.save(bytePwd);
            return SuccessMsg.successMessage(String.format("User has been saved {}", email));
        } else {
            return ErrorMsg.errorMessage("User already exists!");
        }
    }

    /**
     * @return
     */
    @Override
    public ArrayList<BytePwd> getEmailPwdList() {
        return (ArrayList<BytePwd>) byteRep.findAll();
    }

    /**
     * Deletes email password combo
     */
    @Override
    public JsonObject deleteEmailPwdCombo(BytePwd bytePwd) {

        try {

            if (byteRep.findByEmail(bytePwd.getEmail()).isPresent()) {
                byteRep.delete(bytePwd);
                return SuccessMsg.successMessage("User removed!");
            } else {
                return ErrorMsg.errorMessage("Failed to remove user!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to DELETE USER {},{}", bytePwd.getEmail(), e.getLocalizedMessage());
            return ErrorMsg.errorMessage("Failed to remove user!");
        }

    }

}
