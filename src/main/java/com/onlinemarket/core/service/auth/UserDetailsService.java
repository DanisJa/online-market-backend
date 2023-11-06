package com.onlinemarket.core.service.auth;

import com.onlinemarket.core.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private final UserRepo userRepo;

    public UserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        System.out.println(email);
        return userRepo.findUserByEmail(email).orElse(userRepo.findUserByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
