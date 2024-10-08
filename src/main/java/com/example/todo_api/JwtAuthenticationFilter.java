package com.example.todo_api;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String token = getTokenFromRequest(request);
    final String username;
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    username = jwtService.getUsernameFromToken(token);
    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      if(jwtService.isTokenValid(token, userDetails)){
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authToken);
      }

    }

    filterChain.doFilter(request, response);

  }

  private String getTokenFromRequest(HttpServletRequest request) {
    final String authheader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.isNotEmpty(authheader) && authheader.startsWith("Bearer ")) {
      return authheader.substring(7);
    }
    return null;

  }

}
