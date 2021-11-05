package com.api.api.objects;

import com.google.gson.Gson;

public class UserAuth {

    private String userId;
    private String loginId;
    private String email;
    private long iat;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(long iat) {
        this.iat = iat;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public UserAuth(String userId, String loginId, String email, long iat) {
        this.userId = userId;
        this.loginId = loginId;
        this.email = email;
        this.iat = iat;
    }
}
