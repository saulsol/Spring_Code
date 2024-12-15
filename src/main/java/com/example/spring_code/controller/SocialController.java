package com.example.spring_code.controller;

import com.example.spring_code.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public String[] getMemberFromKakao(String accessToken){
        memberService.getKakaoMember(accessToken);
        return new String[] {"a", "b", "c"};
    }

}
