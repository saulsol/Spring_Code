package com.example.spring_code.service.memberService;

import com.example.spring_code.dto.MemberDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);
}
