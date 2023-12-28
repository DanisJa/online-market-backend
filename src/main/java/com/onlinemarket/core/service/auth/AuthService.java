package com.onlinemarket.core.service.auth;

import com.onlinemarket.core.exceptions.auth.UserAlreadyExistsException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.repo.UserRepo;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserLoginDTO;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

    @Value("${security.jwt.secret}")
    String tokenSecret;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO signUp(UserRequestDTO userRequestDTO){
        Optional<User> userWithSameEmail = userRepo.findUserByEmail(userRequestDTO.getEmail());
        Optional<User> userWithSameUsername = userRepo.findUserByUsername(userRequestDTO.getUsername());

        if(userWithSameEmail.isEmpty() && userWithSameUsername.isEmpty()) {
            userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            User user = userRepo.save(userRequestDTO.toEntity());

            return new UserDTO(user);
        }

        throw new UserAlreadyExistsException("User with same mail or username already exists.");
    }

    public UserLoginDTO signIn(UserLoginRequestDTO loginRequestDTO) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            );
        } catch (AuthenticationException e){
            throw new BadCredentialsException(e.getMessage());
        }

        User user = userRepo.findUserByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist."));
        String jwt = jwtService.generateToken(user);

        return new UserLoginDTO(jwt);
    }
}
