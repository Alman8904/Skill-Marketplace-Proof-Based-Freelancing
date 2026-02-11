package com.Skill.Marketplace.SM.Services;

import com.Skill.Marketplace.SM.Exception.UnauthorizedException;
import com.Skill.Marketplace.SM.Security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    public String login(String username, String password) {

        log.info("Login attempt for user: {}", username);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid username or password");
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        log.info("Login successful for user: {}", username);


        return jwtUtil.generateToken(userDetails);
    }
}

