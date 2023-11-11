package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.auth.UserAlreadyExistsException;
import com.onlinemarket.core.service.auth.AuthService;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserLoginDTO;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "jwt-auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequestDTO payload){
        try {
            if(!isValidEmail(payload.getEmail()) || payload.getUsername().isEmpty() || payload.getPassword().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(authService.signUp(payload));
        } catch(UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody UserLoginRequestDTO payload){
        try {
            if(!isValidEmail(payload.getEmail()) || payload.getPassword().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                return ResponseEntity.ok(authService.signIn(payload));
            }
        } catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    Boolean isValidEmail(String email){
        return email != null && !email.isEmpty() && email.matches("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$");
    }
}
