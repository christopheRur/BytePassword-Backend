package com.codelab.BytePassword.Controller;

import com.codelab.BytePassword.Service.AppUserCredts.AppUserCreditsImpl;
import com.codelab.BytePassword.Service.BytePwdServiceImpl;
import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class BytePwdController {

    private BytePwdServiceImpl pmService;
    private AppUserCreditsImpl pmUser;


    public BytePwdController(BytePwdServiceImpl bpService,AppUserCreditsImpl appUser){
        this.pmService=bpService;
        this.pmUser=appUser;
    }

    @PostMapping("/addCredentials")
    public ResponseEntity<?>addCredentials(@RequestBody BytePwd bodyRequest){
        log.info("addCredentials =================================>>"+bodyRequest.toString());

        try {
            if (bodyRequest == null) {

                return ResponseEntity.badRequest().body("No data Found!");

            } else {

                return new ResponseEntity<>(pmService.addUserInfo(bodyRequest) , HttpStatus.OK);
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

}


