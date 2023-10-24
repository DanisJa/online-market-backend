package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.service.UserService;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) {return userService.findById(id);}

    @PostMapping("/register")
    public UserDTO addUser(@RequestBody UserRequestDTO payload) {return userService.addUser(payload);}

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UserRequestDTO payload, @PathVariable String id) {return userService.updateUser(id, payload);}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>(HttpStatusCode.valueOf(200));
    }
}
