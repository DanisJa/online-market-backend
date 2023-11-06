package com.onlinemarket.core.service.auth;

import com.onlinemarket.core.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthService {
    private final UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDetails loadUserByUsername(String username){
        return userRepo.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
