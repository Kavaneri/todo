package com.example.todo_api;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.function.*;
import java.security.Key;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {


    private static final String SECRET_KEY = "hA7kJr92Ld8XpZ5YtFgQs1VwBn3CmD6UoR4eIxPvTqWzNyMlEb2SaG0H9fOj";

    public String getToken(UserDetails user) {
      return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
        
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getClaims(String token){
        return Jwts
        .parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims); 
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String Token){
        return getExpiration(Token).before(new Date());
    }

}
