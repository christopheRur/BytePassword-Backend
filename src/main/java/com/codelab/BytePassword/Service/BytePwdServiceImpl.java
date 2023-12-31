package com.codelab.BytePassword.Service;

import com.codelab.BytePassword.Messages.ErrorMsg;
import com.codelab.BytePassword.Messages.SuccessMsg;
import com.codelab.BytePassword.Repository.BytePwdRepository;
import com.codelab.BytePassword.Service.Encryption.PasswordEncryption;
import com.codelab.BytePassword.Service.Kafka.LogConsumer;
import com.codelab.BytePassword.Service.Kafka.LogProducer;
import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.codelab.BytePassword.Constant.Constants.*;

@Slf4j
@Service
public class BytePwdServiceImpl implements BytePwdService {

    @Autowired
    private BytePwdRepository byteRep;

    public BytePwdServiceImpl(BytePwdRepository byteRep) {
        this.byteRep = byteRep;
    }


    /**
     * Adds user in db
     *
     * @param bytePwd
     * @return
     */
    @Override
    public JsonObject addUserInfo(BytePwd bytePwd) {

        try {
            bytePwd.setOriginal(bytePwd.getPassword());
            String password = PasswordEncryption.encryptDataPassword(bytePwd.getPassword());

            bytePwd.setPassword(password);

            if (byteRep.findByEmail(bytePwd.getEmail()).isEmpty()) {

                byteRep.save(bytePwd);
                bytePwd.setAction(ADD_COMBO_PWD_EMAIL);
                LogProducer.produceLogs(bytePwd);
                return SuccessMsg.successMessage(String.format("User " + bytePwd.getEmail() + " has been added!"));
            } else {
                return ErrorMsg.errorMessage("User " + bytePwd.getEmail() + " already exists!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to add  USER details {},{}", bytePwd.getEmail(), e.getLocalizedMessage());
            return ErrorMsg.errorMessage("Failed to failed to add user info!");
        }
    }

    /**
     * Retrieve email and password and other attributes
     * @return
     */
    @Override
    public ArrayList<BytePwd> getEmailPwdList() {

        BytePwd bytePwd = new BytePwd();
        bytePwd.setAction(VIEWED_CREDENTIALS);
        LogProducer.produceLogs(bytePwd);
        LogConsumer.dataConsumer();


        return (ArrayList<BytePwd>) byteRep.findAll();
    }

    /**
     * @return
     */
    @Override
    public JsonArray getEmailPwdAndEncryptedList() {

        JsonObject json = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        List<BytePwd> encryptedList = byteRep.findAll();

        for (BytePwd bytePwd : encryptedList) {

            json.addProperty("email", bytePwd.getEmail());
            json.addProperty("encryptedPwd", bytePwd.getPassword());
            json.addProperty("original", bytePwd.getOriginal());
            json.addProperty("id", bytePwd.getId());
            jsonArray.add(json);
        }

        return jsonArray;
    }

    /**
     * Deletes email password combo
     */
    @Transactional
    @Override
    public JsonObject deleteEmailPwdCombo(String email) {
            BytePwd bytePwd=new BytePwd();
        try {


            if (byteRep.findByEmail(email).isPresent()) {
                byteRep.deleteByEmail(email);

                bytePwd.setAction(DELETED_USER);
                LogProducer.produceLogs(bytePwd);
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

    /**
     * @param json JsonObject
     * @return
     */
    @Override
    public JsonObject checkId(JsonObject json) {
        JsonObject result = new JsonObject();

        try {


            String email = json.get("email").getAsString();
            if(byteRep.findByEmail(email).isPresent()){
                

                result.addProperty("message","drop");

            }
            else {

                result.addProperty("message","keep");

            }
            return result;


        } catch (Exception e) {
        e.printStackTrace();
        log.error("No id found! {}", e.getLocalizedMessage());
        return ErrorMsg.errorMessage(" id not found!");
    }

    }

}
