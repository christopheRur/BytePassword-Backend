package com.codelab.BytePassword;

import com.codelab.BytePassword.Controller.BytePwdController;
import com.codelab.BytePassword.Service.AppUserCredts.AppUserCreditsImpl;
import com.codelab.BytePassword.Service.BytePwdServiceImpl;
import com.codelab.BytePassword.model.AppUser;
import com.codelab.BytePassword.model.BytePwd;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class BytePasswordApplicationTests {

    @Mock
    private BytePwd bytePwd;
    @Mock
    private AppUser appUser;

    @Mock
    private AppUserCreditsImpl appService;

    @InjectMocks
    private BytePwdController controller;
    @Mock
    private BytePwdServiceImpl service;


    @Test
    public void testInfo() throws IOException {
        bytePwd = new BytePwd();
        bytePwd.setEmail("test@example.com");
        Mockito.when(service.getEmailPwdAndEncryptedList()).thenReturn(new JsonArray());
        ResponseEntity<?> response = controller.getInfo();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testDeleteCred() throws IOException {

        bytePwd = new BytePwd();
        bytePwd.setPassword("password");
        bytePwd.setEmail("test@example.com");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "test@example.com");

        Mockito.when(service.deleteEmailPwdCombo(bytePwd)).thenReturn(jsonObject);
        ResponseEntity<?> response = controller.deleteCredits(bytePwd);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testGetCred() throws IOException {

        bytePwd = new BytePwd();
        bytePwd.setPassword("password");
        bytePwd.setEmail("test@example.com");

        Mockito.when(service.getEmailPwdList()).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = controller.getCredits();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testAddCred() throws IOException {

        bytePwd = new BytePwd();
        bytePwd.setPassword("password");
        bytePwd.setEmail("test@example.com");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "test@example.com");

        Mockito.when(service.addUserInfo(bytePwd)).thenReturn(jsonObject);
        ResponseEntity<?> response = controller.getCredits();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testRegisterUser() throws IOException {

        bytePwd = new BytePwd();
        bytePwd.setPassword("password");
        bytePwd.setEmail("test@example.com");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "test@example.com");

        Mockito.when(appService.registerUser(jsonObject)).thenReturn(jsonObject);
        ResponseEntity<?> response = controller.registerUser(jsonObject);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testLogOutUser() throws IOException {

        bytePwd = new BytePwd();
        bytePwd.setPassword("password");
        bytePwd.setEmail("test@example.com");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "test@example.com");

        Mockito.when(appService.logOut(jsonObject)).thenReturn(jsonObject);
        ResponseEntity<?> response = controller.logOutUser(jsonObject);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testLogInUser() throws IOException {

        bytePwd = new BytePwd();
        bytePwd.setPassword("password");
        bytePwd.setEmail("test@example.com");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "test@example.com");

        Mockito.when(appService.logIn(jsonObject)).thenReturn(jsonObject);
        ResponseEntity<?> response = controller.loginUser(jsonObject);
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }


    @Test
    void contextLoads() {
    }

}
