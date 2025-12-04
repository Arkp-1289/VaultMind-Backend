package com.arkp.VaultMind.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.Objects;

@Service
public class JwtService {

    @Autowired
    CustomUserDetailService userDetailService;

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



    public boolean isValidToken(String token, UserDetails userDetails) {
        try{
          Claims claims= Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                  .getPayload();
          System.out.println(claims.getSubject()+" "+userDetails.getUsername());
      if (claims.getSubject().equals(userDetails.getUsername()) && !(isExpired(claims))){
          System.out.println("valid token");
          return  true;}
      return false;
//            return claims.getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }

    private boolean isExpired(Claims claims) {
        System.out.println("expiration: "+claims.getExpiration().before(new Date()));
       return  claims.getExpiration().before(new Date());

    }


}
