package com.onlinemarket.core.service.auth;

import com.onlinemarket.core.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private final UserRepo userRepo;

    public UserAuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        return userRepo.findByUsernameOrEmail(email).orElseThrow(() ->new UsernameNotFoundException("User "));
    }
}
