package com.example.spring_code.service.memberService;

import com.example.spring_code.dto.MemberDTO;
import com.example.spring_code.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements  MemberService {

    private final MemberRepository memberRepository;


    private void getEmailFromKakaoAccessToken(String accessToken){
        String kakaoGetUserUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+accessToken);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        UriComponents components = UriComponentsBuilder
                .fromHttpUrl(kakaoGetUserUrl)
                .build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(components.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info(response.getBody());
    }


    @Override
    public MemberDTO getKakaoMember(String accessToken) {

        // accessToken 을 이용해서 사용자 정보 가져오기
        getEmailFromKakaoAccessToken(accessToken);


        // 기존에 가입되어 있는 사용자인 경우

        // 기존에 가입되지 않았던 사용자인 경우



        return null;
    }







}
