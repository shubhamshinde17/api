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
        String encodedString = Base64.getEncoder().encodeToString(data.getBytes());
        encodedString = Base64.getEncoder().encodeToString(encodedString.concat(ACCESS_KEY).getBytes());
        StringBuilder returnStr = new StringBuilder();
        for (int i = encodedString.length() - 1; i >= 0; i--) {
            returnStr.append(encodedString.charAt(i));
        }
        encodedString = returnStr.toString().replaceAll("=", "");
        return encodedString;
    }

    public String decryptData(String data) {
        String ACCESS_KEY = appProps.getApplicationProperties("ACCESS_KEY");
        StringBuilder returnStr = new StringBuilder();
        for (int i = data.length() - 1; i >= 0; i--) {
            returnStr.append(data.charAt(i));
        }
        String decryptedBase64Str = new String(Base64.getDecoder().decode(returnStr.toString()));
        decryptedBase64Str = decryptedBase64Str.replaceAll(ACCESS_KEY, "");
        decryptedBase64Str = new String(Base64.getDecoder().decode(decryptedBase64Str));
        return decryptedBase64Str;
    }
}
