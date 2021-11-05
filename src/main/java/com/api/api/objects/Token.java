package com.api.api.objects;

import com.google.gson.Gson;

public class Token {
    private String accessToken;
    private String expiresIn;
    private String refeshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefeshToken() {
        return refeshToken;
    }

    public void setRefeshToken(String refeshToken) {
        this.refeshToken = refeshToken;
    }

    public Token(String accessToken, String expiresIn, String refeshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refeshToken = refeshToken;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
