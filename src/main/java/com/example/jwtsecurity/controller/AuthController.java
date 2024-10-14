package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.dto.LoginRequest;
import com.example.jwtsecurity.entity.User;
import com.example.jwtsecurity.security.JwtUtil;
import com.example.jwtsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/public")
    public class AuthController {

        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private UserDetailsService userDetailsService;

        private final UserService userService;

        @Autowired
        public AuthController(@Lazy UserService userService) {
            this.userService = userService;
        }

        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody User user) {
            try {
                userService.saveUser(user);
                return ResponseEntity.ok("Пользователь успешно зарегистрирован");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @PostMapping("/login")
        public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            if (userDetails != null && passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                String token = JwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

