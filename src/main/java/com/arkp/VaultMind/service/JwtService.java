package com.arkp.VaultMind.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.Objects;

@Service
public class JwtService {

//    SecretKey secretKey=Jwts.SIG.HS256.key().build();
SecretKey secretKey = Keys.hmacShaKeyFor("your-32-char-or-longer-secret-key".getBytes());

    public String generateToken(String userName) {
        Map<String,Object> claims = new HashMap<>();
      return  Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(1000*60*60*5)))
                .signWith(secretKey).compact();


    }


    // here we are extacting user name from the jwt toke
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }



    public boolean isValidToken(String token) {
        try{
          Claims claims= Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                  .getPayload();

//            return claims.getExpiration().before(new Date());
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
