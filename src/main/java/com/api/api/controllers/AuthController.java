package com.api.api.controllers;

import java.util.List;

import com.api.api.constants.AuthEndpoints;
import com.api.api.constants.UserMessage;
import com.api.api.models.*;
import com.api.api.objects.Response;
import com.api.api.objects.Token;
import com.api.api.objects.UserAuth;
import com.api.api.repositories.UserRepository;
import com.api.api.services.AuthService;
import com.api.api.services.CustomEncryption;
import com.api.api.services.LoggerService;
import com.api.api.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequestMapping(value = "api/auth")
@RestController
public class AuthController {
    private static final String APPLICATION_JSON = "application/json";
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomEncryption cypher;

    @PostMapping(value = AuthEndpoints.LOGIN, produces = APPLICATION_JSON)
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            String userPassword = user.getPassword();
            List<User> users = userRepo.getUserByLoginIdOrEmail(user.getLoginId(), user.getEmail());
            if (users.size() > 0) {
                user = users.get(0);
                if (authService.isUserPasswordValid(user, userPassword)) {
                    UserAuth userAuth = new UserAuth(user.getUserId(), user.getLoginId(), user.getEmail(), 0);
                    authService.createToken(userAuth);
                    Token generatedToken = tokenService.getToken();
                    Response response = new Response(200, UserMessage.LOGIN_SUCCESS, "SUCCESS", generatedToken);
                    return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
                } else {
                    Response response = new Response(401, UserMessage.WRONG_USER_PASS, "AUTH_ISSUE", null);
                    return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
                }
            } else {
                Response response = new Response(404, UserMessage.USER_NOT_FOUND, "NOT_FOUND", null);
                return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
            }
        } catch (Exception e) {
            LoggerService.getLogger().info("DEBUG: " + e.toString());
            Response response = new Response(500, UserMessage.INTERNAL_SERVER_ERR, e.toString(), null);
            return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = AuthEndpoints.SIGNUP, produces = APPLICATION_JSON)
    public ResponseEntity<String> signupUser(@RequestBody User user) {
        try {
            if (user != null && user.getEmail() != null && user.getLoginId() != null && user.getPassword() != null) {
                user.setPassword(cypher.encryptData(user.getPassword()));// Encrypt Password to store into DB
                User createdUser = userRepo.save(user);
                UserAuth userAuth = new UserAuth(user.getUserId(), user.getLoginId(), user.getEmail(), 0);
                authService.createToken(userAuth);
                Token generatedToken = tokenService.getToken();
                LoggerService.getLogger().info("Created User: " + createdUser.toString());
                Response response = new Response(200, UserMessage.SIGNUP_DONE + user.getLoginId(), "SIGNUP_DONE",
                        generatedToken);
                return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
            } else {
                LoggerService.getLogger().info("User data: " + user.toString());
                Response response = new Response(406, UserMessage.INSUFFICIENT_DATA, "INSUFFICIENT_DATA", null);
                return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
            }

        } catch (Exception e) {
            LoggerService.getLogger().info("DEBUG: " + e.toString());
            Response response = new Response(500, UserMessage.INTERNAL_SERVER_ERR, e.toString(), null);
            return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
