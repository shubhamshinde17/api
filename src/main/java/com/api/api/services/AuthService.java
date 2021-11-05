package com.api.api.services;

import java.util.Date;

import com.api.api.models.User;
import com.api.api.objects.Token;
import com.api.api.objects.UserAuth;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public static final String EXPIRES_STRING = "3600";

    @Autowired
    private CustomEncryption cypher;

    @Autowired
    private TokenService tokenService;

    public Boolean isAuthenticated(String accessToken) {
        long dateTimeSeconds = new Date().getTime();
        String userAuthString = cypher.decryptData(accessToken);
        UserAuth userAuth = new Gson().fromJson(userAuthString, UserAuth.class);
        if ((userAuth.getIat() + 3600000) > dateTimeSeconds) {
            LoggerService.getLogger().info("Access Token is Valid!");
            return true;
        } else {
            return false;
        }
    }

    public Boolean isUserPasswordValid(User user, String userPassword) {
        String decryptedPassword = cypher.decryptData(user.getPassword());
        if (decryptedPassword.equals(userPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public void createToken(UserAuth userData) {
        userData.setIat(new Date().getTime());
        String accessToken = cypher.encryptData(userData.toString());
        Token token = new Token(accessToken, EXPIRES_STRING, null);
        tokenService.setToken(token);
    }
}
