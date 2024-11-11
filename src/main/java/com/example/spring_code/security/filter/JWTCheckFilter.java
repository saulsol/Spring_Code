package com.example.spring_code.security.filter;

import com.example.spring_code.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    // 최초 로그인 시 필터를 거치지 않게 조치
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info(path);

        return false; // false(체크) / true(체크X)
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("doFilterInternal");

       try {
           String authHeaderStr = request.getHeader("Authorization");
           String accessToken = authHeaderStr.substring(7);
           Map<String, Object> claims = jwtUtil.validateToken(accessToken);
           filterChain.doFilter(request, response);
           log.info(claims);
       }catch (Exception e){
           log.error(e.getMessage());
           Gson gson = new Gson();
           String jsonStr = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
           response.setContentType("application/json; charset=UTF-8");
           PrintWriter printWriter = response.getWriter();
           printWriter.println(jsonStr);
           printWriter.close();
       }


    }
}