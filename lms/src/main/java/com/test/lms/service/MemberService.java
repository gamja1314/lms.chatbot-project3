package com.test.lms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Member;
import com.test.lms.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원 가입
    public Member create(String username, String password, String nickname, String email) {
        
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = new Member();

        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password)); 
        member.setNickname(nickname);
        member.setEmail(email);
        member.setCreateTime(LocalDateTime.now());

        this.memberRepository.save(member);
        return member;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다.: " + username));

        return new User(member.getUsername(), member.getPassword(), new ArrayList<>());
    }
    
}
