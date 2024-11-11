package com.example.spring_code.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;



@Component
public class JWTUtil {

    @Value("${jwt.key}")
    private String injectedKey;

    public String generateKey(Map<String, Object> valueMap, int min){

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(injectedKey.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }


    public Map<String, Object> validateToken(String token){

        Map<String, Object> claim = null;

        try {

            SecretKey key = Keys.hmacShaKeyFor(injectedKey.getBytes("UTF-8"));

            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        }catch (MalformedJwtException e) {
            throw new CustomJWTException("MalFormed");
        }catch (ExpiredJwtException e){
            throw new CustomJWTException("Expired");
        }catch (InvalidClaimException e){
            throw new CustomJWTException("Invalid");
        }catch (JwtException jwtException){
            throw new CustomJWTException("JWTError");
        }catch (Exception e){
            throw new CustomJWTException("Error");
        }

        return claim;
    }



}
