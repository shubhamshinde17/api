package com.api.api.services;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomEncryption {
    @Autowired
    private ApplicationProperties appProps;

    private String encryptedData;
    private String decryptedData;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getDecryptedData() {
        return decryptedData;
    }

    public void setDecryptedData(String decryptedData) {
        this.decryptedData = decryptedData;
    }

    public String encryptData(String data) {
        String ACCESS_KEY = appProps.getApplicationProperties("ACCESS_KEY");
        String base64Str = Base64.getEncoder().encodeToString(data.getBytes());
        base64Str = Base64.getEncoder().encodeToString(base64Str.concat(ACCESS_KEY).getBytes());
        return base64Str;
    }

    public String decryptData(String data) {
        String ACCESS_KEY = appProps.getApplicationProperties("ACCESS_KEY");
        String decryptedBase64Str = new String(Base64.getDecoder().decode(data));
        decryptedBase64Str = decryptedBase64Str.replaceAll(ACCESS_KEY, "");
        decryptedBase64Str = new String(Base64.getDecoder().decode(decryptedBase64Str));
        return decryptedBase64Str;
    }
}
