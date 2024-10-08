package com.leetreader.leetReader.config.security.filters;

import com.leetreader.leetReader.config.security.authentication.CustomAuthentication;
import com.leetreader.leetReader.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;
    /*
    1. Create Authentication which is not authenticated yet.
    2- delegate the authentication to the authentication manager
    3- get back the authentication from authentication manager.
    4- if the object is authenticated send the request to the next filters chain.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        String key =String.valueOf(request.getHeader("key"));
        CustomAuthentication ca = new CustomAuthentication(false , key);

        var  customAuthManager= customAuthenticationManager.authenticate(ca);

        if(customAuthManager.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(customAuthManager);
            filterChain.doFilter(request, response);
        }

////        System.out.println("⛔⛔⛔⛔ not allowed!" + request.getRequestURI());
//        if (!Objects.equals(request.getHeader("x-hello"), "ibrahim")) {
////            reject the request!🫷🫷🫷🫷🫷
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Content-Type", "text/plain;charset=UTF-8");
//            response.getWriter().write("⛔⛔⛔⛔ not allowed");
//            return;
//        }
//
//        filterChain.doFilter(request, response);

    }
}
