package com.example.spring_code.security;

import com.example.spring_code.domian.Member;
import com.example.spring_code.dto.MemberDTO;
import com.example.spring_code.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetails implements UserDetailsService {

    private final MemberRepository memberRepository;


    // id 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUserName 동작");
        Member member = memberRepository.getWithRoles(username);

        if(member == null){
            throw new UsernameNotFoundException("Not Found");
        }

        MemberDTO memberDTO = new MemberDTO(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList()
                        .stream()
                        .map(memberRole -> memberRole.name()).collect(Collectors.toList())// Enun => String 변환
        );


        return memberDTO;
    }
}
