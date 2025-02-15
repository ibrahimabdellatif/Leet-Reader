package com.leetreader.leetReader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        authorizeHttp -> {
                            authorizeHttp.requestMatchers("/api/v1/users/adduser").permitAll();
                            authorizeHttp.requestMatchers("/api/v1/articles").permitAll();
                            authorizeHttp.anyRequest().authenticated();
                        }
                )
//                The second parameter 'true' to enforce redirect user to specific url
//                .formLogin(l -> l.defaultSuccessUrl("https://www.google.com"))
//                .logout(l -> l.logoutSuccessUrl("https://www.youtube.com"))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebClient webClient(){
//        return WebClient.builder().build();
//    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
