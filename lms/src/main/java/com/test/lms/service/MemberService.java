package com.test.lms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Exp;
import com.test.lms.entity.Member;
import com.test.lms.exception.DataNotFoundException;
import com.test.lms.repository.ExpRepository;
import com.test.lms.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ExpRepository expRepository;

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

//      return new User(member.getUsername(), member.getPassword(), new ArrayList<>()); // 유저 정보를 반환
        return org.springframework.security.core.userdetails.User
              .withUsername(member.getUsername())
              .password(member.getPassword())
              .build();
  }

	public Member findByUsername(String username) {
		Optional<Member> Member = this.memberRepository.findByUsername(username);
		if (Member.isPresent()) {
			return Member.get();
		} else {
			throw new DataNotFoundException("회원정보를 찾을 수 없습니다.");
		}
	}
	
		// 랭크 업데이트
	   @Transactional
	    public void updateMemberRank(Long memberNum) {
	        Member member = memberRepository.findById(memberNum)
	            .orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
	        
	        Exp exp = expRepository.findByMember(member)
	            .orElseThrow(() -> new DataNotFoundException("회원의 경험치를 찾을 수 없습니다."));
	        
	        int expPoints = exp.getExpPoints();
	        String newRank;

	        if (expPoints < 100) {
	            newRank = "bronze";
	        } else if (expPoints < 200) {
	            newRank = "silver";
	        } else if (expPoints < 300) {
	            newRank = "gold";
	        } else if (expPoints < 500) {
	        	newRank = "diamond";
	        } else {
	        	newRank = "master";
	        }
	        
	        member.setUserRank(newRank);
	        memberRepository.save(member);
	    }

	   	// 경험치 추가
	    @Transactional
	    public void addExpPoints(Long memberNum, int points) {
	        Member member = memberRepository.findById(memberNum)
	            .orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
	        
	        Exp exp = expRepository.findByMember(member)
	            .orElse(new Exp());

	        exp.setMember(member);
	        exp.setExpPoints(exp.getExpPoints() + points);
	        expRepository.save(exp);

	        updateMemberRank(memberNum);
	    }
	
	    // 회원 경험치 조회
	    public Exp findExpByMember(Member member) {
	        return expRepository.findByMember(member)
	                .orElseThrow(() -> new DataNotFoundException("회원의 경험치를 찾을 수 없습니다."));
	    }
}
