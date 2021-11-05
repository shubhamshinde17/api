package com.api.api.objects;

import com.google.gson.Gson;

public class Response {

    private int code;
    private String userMessage;
    private String devMessage;
    private Object data;

    public Response(int code, String userMessage, String devMessage, Object data) {
        this.code = code;
        this.userMessage = userMessage;
        this.devMessage = devMessage;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
