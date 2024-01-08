package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.ApiExceptions.ConflictException;
import com.onlinemarket.core.exceptions.general.BadRequestException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.UserService;
import com.onlinemarket.rest.dto.responses.ApiResponse;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@SecurityRequirement(name = "jwt-auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> findAll(){
        return ResponseEntity.ok(new ApiResponse<>(true, userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, userService.findById(id)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@RequestBody UserRequestDTO payload, @PathVariable String id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, userService.updateUser(id, payload)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(BadRequestException error) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, error.getMessage()));
        } catch
        (Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
        } catch(ConflictException error) {
            return ResponseEntity.status(409).body(new ApiResponse<>(false, error.getMessage()));
        } catch
         (ResourceNotFoundException error) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch (Exception error) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted"));
    }
}
