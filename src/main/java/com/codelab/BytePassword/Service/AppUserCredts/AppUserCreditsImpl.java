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

import java.util.ArrayList;
import java.util.Optional;

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
        String email = String.valueOf(res.get("email"));
        Optional<AppUser> userApp = appUserRepo.findUserByEmail(email);

        if (userApp.isPresent()) {

            res.addProperty("logout", Boolean.TRUE);
            res.addProperty("message", "Successfully Logged Out.");

            BytePwd bytePwd = new BytePwd();

            bytePwd.setAction(LOGOUT);
            bytePwd.setTimestamp(ToolBox.stampTimeOfLogs());
            bytePwd.setEmail(email);
            //LogProducer.produceLogs(bytePwd);

            return ToolBox.authResponse("Successfully Logged Out.", Boolean.TRUE);
        }
        return ToolBox.authResponse("Failed to logout.", Boolean.FALSE);
    }

    /**
     * Signs user in the app
     *
     * @param res
     * @return
     */
    public JsonObject logIn(JsonObject res) {

        try {
            String email = String.valueOf(res.get("email"));
            String password = String.valueOf(res.get("password"));


            if (email != null && password != null) {

                Optional<AppUser> userApp = appUserRepo.findUserByEmail(email);


                if (userApp.isPresent()) {

                    String encryptedPwdFromDB = userApp.get().getPassword(); // Assuming getPassword()

                    if (PasswordEncryption.validatePassword(password, encryptedPwdFromDB)) {


                        BytePwd bytePwd = new BytePwd();
                        bytePwd.setAction(LOGIN);
                        bytePwd.setTimestamp(ToolBox.stampTimeOfLogs());
                        bytePwd.setEmail(email);
                       // LogProducer.produceLogs(bytePwd);


                        return ToolBox.authResponse("Successfully Logged In.", Boolean.TRUE);


                    } else {


                        return ToolBox.authResponse("Unable to Login", Boolean.FALSE);
                    }
                } else {

                    return ToolBox.authResponse("Invalid Login credentials", Boolean.FALSE);
                }


            } else {

                return ToolBox.authResponse("Unable to Login, make sure you enter your email and password.", Boolean.FALSE);

            }
        } catch (Exception e) {
            log.error("Failed to log in USER {},{}", res.get("email"), e.getLocalizedMessage());
            e.printStackTrace();
            return ErrorMsg.errorMessage("Failed to log in user!");
        }


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


            String password = PasswordEncryption.encryptPassword(String.valueOf(res.get("password")));
            String hint = String.valueOf(res.get("hint"));

            AppUser appUser = new AppUser();

            appUser.setEmail(email);
            appUser.setPassword(password);
            appUser.setHint(hint);


            if (appUserRepo.findUserByEmail(email).isEmpty()) {
                log.info("ENCRYPTED PASSWORD ----=>{}", appUser.getPassword());

                appUserRepo.save(appUser);

                BytePwd bytePwd = new BytePwd();
                bytePwd.setAction(REGISTER_USER);
                bytePwd.setTimestamp(ToolBox.stampTimeOfLogs());
                bytePwd.setEmail(email);
                LogProducer.produceLogs(bytePwd);

                return SuccessMsg.successMessage("User's with username: " + email + " created!");
            } else {

                return ErrorMsg.errorMessage("User already exists!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to Register USER {},{}", email, e.getLocalizedMessage());
            return ErrorMsg.errorMessage("Failed to register user!");
        }
    }

    /**
     * @return
     */
    @Override
    public ArrayList<AppUser> getEmailPwdList() {
        return (ArrayList<AppUser>) appUserRepo.findAll();
    }


}
