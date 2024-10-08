package com.leetreader.leetReader.config;

import com.leetreader.leetReader.config.security.filters.CustomAuthenticationFilter;
import com.leetreader.leetReader.config.security.managers.CustomAuthenticationManager;
import com.leetreader.leetReader.config.security.providers.CustomAuthenticationProvider;
import com.leetreader.leetReader.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {
    //    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserService userService;
//    laur play list for spring security in 2022
/*
    he is starting to tell us why we need to spring security it use for authentication & authorization
    the first one is to tell to the web app what is your identity are you user of my app or not
    the second one tell us what is your permission after we check you identity, and you are an authentication man

    Then we when you use basic auth and you make a request your username and password send with http header to the server to check you identity
    and it is encoded with base 64

    Then it tell us what is the difference between (Encoding - Encryption - Hashing)
    1. Encoding -> is a way to transform something like string to other string with way like 1234 -> 4321
    so the roll here you just revers the string and we can revers it back to the original
    2. Encryption -> it is like encoding but you need to the key 'private key' to make the reverse conversion again
    3. Hashing -> the previous two methods are Two direction like you go from original to created and from created back to the original
    BUT hashing is one way you can go in one direction from original to hashing you can't back to the original once you have the hashed
    and this is perfect for password and storing it to the database.

    Then we learn how to create a password in memory and to configure you spring security you need to create configuration class,
    and he is talk about password encoding that use BCrypt
 */

//now we don't need to this bean because we implement the UserDetailsService interface author
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager us = new InMemoryUserDetailsManager();
//       UserDetails user =  User.withUsername("ibrahim")
//                .password("12345")
//                .authorities("Read")
//                        .build();
//        us.createUser(user);
//        return us;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationManager customAuthenticationManager) throws Exception {
        return http
                .authorizeHttpRequests(
                        authorizeHttp -> {
                            authorizeHttp.requestMatchers("/").permitAll();
                            authorizeHttp.anyRequest().authenticated();
                        }
                )
//                The second parameter 'true' to enforce redirect user to specific url
                .formLogin(l -> l.defaultSuccessUrl("https://www.google.com"))
                .logout(l -> l.logoutSuccessUrl("https://www.youtube.com"))
                .httpBasic(Customizer.withDefaults()) // we need it when implement normal username and password authentication method
                .addFilterAfter(new CustomAuthenticationFilter(customAuthenticationManager), AuthorizationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}
