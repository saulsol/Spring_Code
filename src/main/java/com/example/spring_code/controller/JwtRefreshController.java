package com.example.spring_code.controller;

import com.example.spring_code.util.CustomJWTException;
import com.example.spring_code.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class JwtRefreshController {

    private final JWTUtil jwtUtil;

    @RequestMapping("/api/member/register")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
                                       String refreshToken){

        if(refreshToken == null){
            throw new CustomJWTException("NULL_REFRESH");
        }

        if(authHeader == null || authHeader.length() < 7){
            throw new CustomJWTException("INVALID_STRING");
        }

        String accessToken = authHeader.substring(7);

        if(checkExpiredToken(accessToken) == false){
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // refresh 검증
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);
        String newAccessToken = jwtUtil.generateToken(claims, 10);

        String newRefreshToken = checkTime((Integer) claims.get("exp")) == true
                ? jwtUtil.generateToken(claims, 60*24)
                : refreshToken;

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }


    private boolean checkTime(Integer exp){

        Date expDate = new Date((long)exp * 1000);

        long gap = expDate.getTime() - System.currentTimeMillis();

        long leftMin = gap/(1000 * 60);

        return leftMin < 60;


    }



    private boolean checkExpiredToken(String token){

        try {
            jwtUtil.validateToken(token);
        }catch (CustomJWTException e){
            if(e.getMessage().equals("expired")){
                return true;
            }
        }
        return false;
    }


}
