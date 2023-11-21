package com.codelab.BytePassword.Service;

import com.codelab.BytePassword.Messages.ErrorMsg;
import com.codelab.BytePassword.Messages.SuccessMsg;
import com.codelab.BytePassword.Repository.BytePwdRepository;
import com.codelab.BytePassword.Service.Encryption.PasswordEncryption;
import com.codelab.BytePassword.Service.Kafka.LogProducer;
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

    public BytePwdServiceImpl(BytePwdRepository byteRep) {
        this.byteRep = byteRep;
    }


    /**
     * Adds user
     * @param bytePwd
     * @return
     */
    @Override
    public JsonObject addUserInfo(BytePwd bytePwd) {
        log.info("-------RTRTTRTRTRRTRTRTRRTRTRTRTRTRTRTRTTRRTRT-=====- " + bytePwd.toString());

        String password = PasswordEncryption.encryptPwd(bytePwd.getPassword());

        bytePwd.setPassword(password);


        if (byteRep.findByEmail(bytePwd.getEmail()).isEmpty()) {
            LogProducer.produceLogs(bytePwd);
            byteRep.save(bytePwd);
            return SuccessMsg.successMessage(String.format("User has been saved {}", bytePwd.getEmail()));
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
