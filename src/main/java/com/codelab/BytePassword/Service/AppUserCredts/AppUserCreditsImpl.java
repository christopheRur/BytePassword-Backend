package com.codelab.BytePassword.Service.AppUserCredts;

import com.codelab.BytePassword.Messages.ErrorMsg;
import com.codelab.BytePassword.Messages.SuccessMsg;
import com.codelab.BytePassword.Repository.AppUserRepository;
import com.codelab.BytePassword.Service.Encryption.PasswordEncryption;
import com.codelab.BytePassword.model.AppUser;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

                if (appUserRepo.findUserByEmail(email).isPresent()) {

                    String decryptedPwd = PasswordEncryption
                            .decryptPwd(String.valueOf(appUserRepo
                                    .findPasswordByEmail(password)));

                    if (decryptedPwd.equals(password)) {

                        res.addProperty("verified", Boolean.TRUE);

                        return res;
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
                log.info("ENCRYPTED PASSWORD ----=>{}", appUser);
                appUserRepo.save(appUser);
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
