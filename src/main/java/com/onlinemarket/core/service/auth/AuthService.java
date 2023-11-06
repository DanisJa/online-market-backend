package com.onlinemarket.core.service.auth;

import com.onlinemarket.core.exceptions.auth.InvalidCredentialsException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.repo.UserRepo;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserLoginDTO;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO signUp(UserRequestDTO userRequestDTO){
        Optional<User> userWithSameEmail = userRepo.findUserByEmail(userRequestDTO.getEmail());

        if(userWithSameEmail.isEmpty()) {
            String password = userRequestDTO.getPassword();
            userRequestDTO.setPassword(passwordEncoder.encode(password));
            User user = userRepo.save(userRequestDTO.toEntity());

            return new UserDTO(user);
        }

        throw new InvalidCredentialsException("User with same mail already exists.");
    }

    public UserLoginDTO signIn(UserLoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        User user = userRepo.findUserByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User with given email does not exist."));
        String jwt = jwtService.generateToken(user);

        return new UserLoginDTO(jwt);
    }
}
