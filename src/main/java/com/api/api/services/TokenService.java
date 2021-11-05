package com.api.api.services;

import com.api.api.objects.Token;
import com.google.gson.Gson;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
