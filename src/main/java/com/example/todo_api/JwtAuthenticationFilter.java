package com.example.todo_api;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);

        

    }

    private String getTokenFromRequest(HttpServletRequest request) {
      final String authheader = request.getHeader(HttpHeaders.AUTHORIZATION);

      if(StringUtils.isNotEmpty(authheader) && authheader.startsWith("Bearer ")){
        return authheader.substring(7);
      }
      return null;

    }

}
