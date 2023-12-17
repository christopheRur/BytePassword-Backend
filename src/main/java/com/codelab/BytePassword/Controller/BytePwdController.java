package com.codelab.BytePassword.Controller;

import com.codelab.BytePassword.Service.AppUserCredts.AppUserCreditsImpl;
import com.codelab.BytePassword.Service.BytePwdServiceImpl;
import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class BytePwdController {

    private BytePwdServiceImpl pmService;
    private AppUserCreditsImpl pmUser;


    public BytePwdController(BytePwdServiceImpl bpService, AppUserCreditsImpl appUser) {
        this.pmService = bpService;
        this.pmUser = appUser;
    }


    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody JsonObject bodyRequest) {

        try {
            if (bodyRequest.toString().isEmpty()) {

                return ResponseEntity.badRequest().body("No data Found!");

            } else {

                return new ResponseEntity<>(pmUser.logIn(bodyRequest), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.error("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to login user..");
        }
    }

    @PostMapping("/log-out-user")
    public ResponseEntity<?> logOutUser(@RequestBody JsonObject bodyRequest) {


        try {
            if (bodyRequest.toString().isEmpty()) {

                return ResponseEntity.badRequest().body("No data Found!");

            } else {

                return new ResponseEntity<>(pmUser.logOut(bodyRequest), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.error("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to log-out user.");
        }
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody JsonObject bodyRequest) {
        log.info("addCredentials --->" + bodyRequest.toString());

        try {
            if (bodyRequest.toString().isEmpty()) {

                return ResponseEntity.badRequest().body("No data Found!");

            } else {

                return new ResponseEntity<>(pmUser.registerUser(bodyRequest), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.error("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to log-out user.");
        }
    }

    @PostMapping("/addCredentials")
    public ResponseEntity<?> addCredentials(@RequestBody BytePwd bodyRequest) {
        log.info("addCredentials ---=>>" + bodyRequest.toString());

        try {
            if (bodyRequest.toString().isEmpty()) {

                return ResponseEntity.badRequest().body("No data Found!");

            } else {

                return new ResponseEntity<>(pmService.addUserInfo(bodyRequest), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.error("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
        }
    }


    @GetMapping("/getCredentials")
    public ResponseEntity<?> getCredits() {
        try {
            if (pmService == null) {

                return ResponseEntity.badRequest().body("No DATA Found!");

            } else {


                return new ResponseEntity<>(pmService.getEmailPwdList(), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.info("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
        }
    }


    @GetMapping("/get-info")
    public ResponseEntity<?> getInfo() {
        try {
            if (pmService == null) {

                return ResponseEntity.badRequest().body("No DATA Found!");

            } else {


                return new ResponseEntity<>(pmService.getEmailPwdAndEncryptedList(), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.info("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
        }
    }


    @DeleteMapping("/deleteCredentials/{email}")
    public ResponseEntity<?> deleteCredentials(@PathVariable String email) {
        try {
            if (pmService == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Service Unavailable. Please try again later.");
            }

            JsonObject result = pmService.deleteEmailPwdCombo(email);
            if (result != null) {
                log.info("Service deleted ==> " + result);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            log.error("Error occurred while deleting credentials: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error occurred, unable to delete credentials.");
        }
    }
    @PostMapping("/verify_id")
    public ResponseEntity<?> verifyId(@RequestBody JsonObject bodyRequest) {

        try {
            if (bodyRequest.toString().isEmpty()) {

                return ResponseEntity.badRequest().body("No data Found!");

            } else {

                return new ResponseEntity<>(pmService.checkId(bodyRequest), HttpStatus.OK);
            }

        } catch (Exception e) {

            log.error("==>" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to login user..");
        }
    }
}


