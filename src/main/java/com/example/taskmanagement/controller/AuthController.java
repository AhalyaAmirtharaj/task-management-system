package com.example.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.security.JwtService;
import com.example.taskmanagement.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return userService.registerUser(user);

    }
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        System.out.println("Username from React: " + user.getUsername());
        System.out.println("Password from React: " + user.getPassword());

        User dbUser = userService.findByUsername(user.getUsername());

        if(dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            return jwtService.generateToken(dbUser.getUsername());
        }

        throw new RuntimeException("Invalid credentials");
    }

}