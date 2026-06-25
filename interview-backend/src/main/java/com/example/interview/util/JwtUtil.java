package com.example.interview.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET =
            "mySuperSecretKeyForJwtAuthentication1234567890"; // min 32 chars

    private static final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());
    public static String generateToken(String email,String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public static String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public static Key getKey(){
        return key;
    }
}
