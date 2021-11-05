package com.api.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ApplicationProperties {
    @Autowired
    private Environment environments;

    public String getApplicationProperties(String propKey) {
        String property = environments.getProperty(propKey);
        return property;
    }
}
