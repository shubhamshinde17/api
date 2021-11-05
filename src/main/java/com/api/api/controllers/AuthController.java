package com.api.api.controllers;

import java.util.List;

import com.api.api.models.*;
import com.api.api.objects.Response;
import com.api.api.objects.Token;
import com.api.api.objects.UserAuth;
import com.api.api.repositories.UserRepository;
import com.api.api.services.AuthService;
import com.api.api.services.LoggerService;
import com.api.api.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/login", produces = APPLICATION_JSON)
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
                    Response response = new Response(200, "User logged In!", "Success", generatedToken);
                    return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
                } else {
                    Response response = new Response(401, "Wrong Username or Password!", "AUTH_ISSUE", null);
                    return new ResponseEntity<String>(response.toString(), HttpStatus.UNAUTHORIZED);
                }
            } else {
                Response response = new Response(404, "User not found!", "NOT_FOUND", null);
                return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LoggerService.getLogger().info("DEBUG: " + e.toString());
            Response response = new Response(500, "Internal Server Error!", "SERVER_ERROR", null);
            return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
