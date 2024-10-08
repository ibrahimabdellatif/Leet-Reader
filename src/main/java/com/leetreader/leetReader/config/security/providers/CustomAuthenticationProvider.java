package com.leetreader.leetReader.config.security.providers;

import com.leetreader.leetReader.config.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${my.very.secret.key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
        String headerKey = customAuthentication.getKey();

        if (key.equals(headerKey)) {
            return new CustomAuthentication(true , null);
        }

        throw new BadCredentialsException("Oh, ⛔🫷⛔Bad credentials");
    }

//   this method used to tell to the provider which Authentication class to use in authentication process
    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
