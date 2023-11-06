package com.onlinemarket.rest.filters;

import com.mongodb.lang.NonNull;
import com.onlinemarket.core.service.UserService;
import com.onlinemarket.core.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = req.getHeader("Authorization");
        final String jwt;
        final String email;

        if(StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer")){
            filterChain.doFilter(req, res);
            return;
        }

        jwt = header.substring(7);
        email = jwtService.extractUser(jwt);

        if(StringUtils.isNotEmpty(email) && SecurityContextHolder.getContext().getAuthentication() == null) {

        }
    }
}