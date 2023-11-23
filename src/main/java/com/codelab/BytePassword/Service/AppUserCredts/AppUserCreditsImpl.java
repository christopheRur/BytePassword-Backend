package com.codelab.BytePassword.Service.AppUserCredts;

import com.codelab.BytePassword.Messages.ErrorMsg;
import com.codelab.BytePassword.Messages.SuccessMsg;
import com.codelab.BytePassword.Repository.AppUserRepository;
import com.codelab.BytePassword.Service.Encryption.PasswordEncryption;
import com.codelab.BytePassword.Service.Kafka.LogProducer;
import com.codelab.BytePassword.Utils.ToolBox;
import com.codelab.BytePassword.model.AppUser;
import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.codelab.BytePassword.Constant.Constants.*;

@Slf4j
@Service
public class AppUserCreditsImpl implements UserServices {

    @Autowired
    AppUserRepository appUserRepo;

    public AppUserCreditsImpl(AppUserRepository appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    /**
     * Logs out the user
     */
    @Override
    public JsonObject logOut(JsonObject res) {

        res.addProperty("logout", Boolean.TRUE);

        BytePwd bytePwd= new BytePwd();

        bytePwd.setAction(LOGOUT);
        bytePwd.setTimestamp(ToolBox.stampTimeOfLogs());
        bytePwd.setEmail(String.valueOf(res.get("email")));
        LogProducer.produceLogs(bytePwd);

        return res;
    }

    /**
     * @param res
     * @return
     */
    @Override
    public JsonObject logIn(JsonObject res) {

        String email = String.valueOf(res.get("email"));
        String password = String.valueOf(res.get("password"));

        try {

            if ((email != null && password != null)) {
                PasswordEncryption.encryptPwd(password);

                if (appUserRepo.findUserByEmail(email).isPresent()) {

                    String decryptedPwd = String.valueOf(appUserRepo
                                    .findPasswordByEmail(password));
                    log.info("Encrypted password================-------------================> " +decryptedPwd);


                    if (decryptedPwd.equals(password)) {

                        res.addProperty("verified", Boolean.TRUE);


                        BytePwd bytePwd= new BytePwd();

                        bytePwd.setAction(LOGIN);
                        bytePwd.setTimestamp(ToolBox.stampTimeOfLogs());
                        bytePwd.setEmail(email);
                        LogProducer.produceLogs(bytePwd);

                        return res;
                    }
                    else {
                        ErrorMsg.errorMessage("Failed to login with email+password provided.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to login USER {},{}", email, e.getLocalizedMessage());
            return ErrorMsg.errorMessage("Failed to login user!");
        }

        return null;
    }

    /**
     * Registers a user
     *
     * @param res
     * @return
     */
    @Override
    public JsonObject registerUser(JsonObject res) {

        String email = String.valueOf(res.get("email"));

        try {

            String password = PasswordEncryption.encryptPwd(String.valueOf(res.get("password")));
            String hint = String.valueOf(res.get("hint"));

            AppUser appUser = new AppUser();

            appUser.setEmail(email);
            appUser.setPassword(password);
            appUser.setHint(hint);


            if (appUserRepo.findUserByEmail(email).isEmpty()) {
                log.info("ENCRYPTED PASSWORD ----=>{}", appUser.getPassword());

                appUserRepo.save(appUser);

                BytePwd bytePwd= new BytePwd();
                bytePwd.setAction(REGISTER_USER);
                bytePwd.setTimestamp(ToolBox.stampTimeOfLogs());
                bytePwd.setEmail(email);
                LogProducer.produceLogs(bytePwd);

                return SuccessMsg.successMessage(String.format("User's with username {} created!", email));
            } else {

                return ErrorMsg.errorMessage("User already exists!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to Register USER {},{}", email, e.getLocalizedMessage());
            return ErrorMsg.errorMessage("Failed to register user!");
        }
    }
}
