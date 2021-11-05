package com.api.api.services;

import org.springframework.stereotype.Service;

import com.api.api.ApiApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoggerService {
    private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    public static Logger getLogger() {
        return logger;
    }
}
