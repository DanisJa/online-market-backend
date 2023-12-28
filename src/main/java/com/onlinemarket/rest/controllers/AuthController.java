package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.auth.UserAlreadyExistsException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.UserService;
import com.onlinemarket.core.service.auth.AuthService;
import com.onlinemarket.core.service.auth.JwtService;
import com.onlinemarket.rest.dto.responses.ApiResponse;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserLoginDTO;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "jwt-auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthService authService, JwtService jwtService, UserService userService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/currentUser")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(@RequestHeader("Authorization") String jwtWithBearer){
        try{
            String jwt = jwtWithBearer.replace("Bearer ", "");
            String username = jwtService.extractUserName(jwt);
            UserDTO currentUser = userService.findByUsernameOrEmail(username);
            return ResponseEntity.ok(new ApiResponse<>(true, currentUser));
        } catch(ResourceNotFoundException exception){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, exception.getMessage()));
        } catch (Exception exception){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, exception.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody UserRequestDTO payload){
        try {
            if(!isValidEmail(payload.getEmail()) || payload.getUsername().isEmpty() || payload.getPassword().isEmpty() || payload.getPassword() == null || payload.getEmail() == null || payload.getUsername() == null || payload.getUserType() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, authService.signUp(payload)));
        } catch(UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(false, "Couldn't register user."));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginDTO>> login(@RequestBody UserLoginRequestDTO payload){
        try {
            if(!isValidEmail(payload.getEmail()) || payload.getPassword().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                return ResponseEntity.ok(new ApiResponse<>(true, authService.signIn(payload)));
            }
        }  catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Account not found."));
        } catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "Bad credentials."));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    Boolean isValidEmail(String email){
        return email != null && !email.isEmpty() && email.matches("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$");
    }
}
