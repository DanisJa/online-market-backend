package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.service.auth.AuthService;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserLoginDTO;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequestDTO payload){
        return ResponseEntity.ok(authService.signUp(payload));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody UserLoginRequestDTO payload){
        return ResponseEntity.ok(authService.signIn(payload));
    }
}
