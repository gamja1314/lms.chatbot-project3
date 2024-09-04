package com.test.lms.service;

import org.springframework.stereotype.Service;

import com.test.lms.entity.Member;
import com.test.lms.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // member 가입
    public Member create(String username, String password, String nickname, String email) {
        
        Member member = new Member();

        member.setUsername(username);
        member.setPassword(password);
        member.setNickname(nickname);
        member.setEmail(email);

        this.memberRepository.save(member);

        return member;
    }

}
