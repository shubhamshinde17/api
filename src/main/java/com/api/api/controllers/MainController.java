package com.api.api.controllers;

import java.util.ArrayList;
import java.util.List;

import com.api.api.models.*;
import com.api.api.objects.Response;
import com.api.api.repositories.UserRepository;
import com.api.api.services.AuthService;
import com.api.api.services.LoggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private static final String APPLICATION_JSON = "application/json";

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return new ResponseEntity<String>("Welcome!", HttpStatus.OK);
    }

    @PostMapping(value = "/users", produces = APPLICATION_JSON)
    public ResponseEntity<String> getAllUsers(@RequestHeader HttpHeaders headers) {
        try {
            String authBearerToken = headers.get("Authorization").get(0).toString().replaceAll("Bearer ", "");
            if (authService.isAuthenticated(authBearerToken)) {
                List<User> users = new ArrayList<User>();
                users = userRepo.findAll();
                LoggerService.getLogger().info("Users: " + users.toString());
                Response response = new Response(200, "Success!", "Success", users.toString());
                return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
            } else {
                Response response = new Response(401, "Please login and try again!", "AUTH_FAILED", null);
                return new ResponseEntity<String>(response.toString(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.getStackTrace();
            Response response = new Response(500, "Internal Server Error!", "SERVER_ERROR", null);
            return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/users", produces = APPLICATION_JSON)
    public ResponseEntity<String> saveUser(@RequestBody User user, @RequestHeader HttpHeaders headers) {
        try {
            String authBearerToken = headers.get("Authorization").get(0).toString().replaceAll("Bearer ", "");
            if (authService.isAuthenticated(authBearerToken)) {
                User saveduser = new User();
                saveduser = userRepo.save(user);
                LoggerService.getLogger().info("Users: " + saveduser.toString());
                Response response = new Response(200, "Success!", "Success", saveduser.toString());
                return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
            } else {
                Response response = new Response(401, "Please login and try again!", "AUTH_FAILED", null);
                return new ResponseEntity<String>(response.toString(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.getStackTrace();
            Response response = new Response(500, "Internal Server Error!", "SERVER_ERROR", null);
            return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/users/{id}", produces = APPLICATION_JSON)
    public ResponseEntity<String> getUserById(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        try {
            String authBearerToken = headers.get("Authorization").get(0).toString().replaceAll("Bearer ", "");
            if (authService.isAuthenticated(authBearerToken)) {
                Response response = new Response(200, "Success!", "Success", null);
                User saveduser = userRepo.findById(id).get();
                saveduser.setPassword("<HIDDEN>");
                response.setData(saveduser);
                return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
            } else {
                Response response = new Response(401, "Please login and try again!", "AUTH_FAILED", null);
                return new ResponseEntity<String>(response.toString(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response(500, "Internal Server Error!", "SERVER_ERROR", null);
            return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
