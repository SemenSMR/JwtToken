package com.example.jwtsecurity.security;


import org.neo4j.driver.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationProvider(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String token = (String) authentication.getCredentials();

        UserDetails userDetails = JwtUtil.extractUserDetailsFromToken(token, userDetailsService);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid token");
        }

        return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
