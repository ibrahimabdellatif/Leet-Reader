package com.leetreader.leetReader.config.security.managers;

import com.leetreader.leetReader.config.security.providers.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
//    if you want to use manager you need to have provider.

    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (customAuthenticationProvider.supports(authentication.getClass())) {
            System.out.println("Hello I'm you Custom Provider 😘👌");
          return   customAuthenticationProvider.authenticate(authentication);
        }
        throw new BadCredentialsException("Oh, ⛔🫷⛔Bad credentials from manager");
    }
}
