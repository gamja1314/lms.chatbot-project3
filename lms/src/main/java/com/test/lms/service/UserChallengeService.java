package com.test.lms.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Challenge;
import com.test.lms.entity.Member;
import com.test.lms.entity.UserChallenge;
import com.test.lms.exception.DataNotFoundException;
import com.test.lms.repository.ChallengeRepository;
import com.test.lms.repository.MemberRepository;
import com.test.lms.repository.UserChallengeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final UserChallengeRepo userChallengeRepo;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    // 활성화 중인 챌린지
    public boolean isUserParticipatingInChallenge(Long memberNum, Long challengeId) {
        Member member = memberRepository.findById(memberNum).orElse(null);
        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        UserChallenge userChallenge = userChallengeRepo.findByMemberAndChallenge(member, challenge).orElse(null);

        if (userChallenge == null) {
            return false;
        }

        return userChallenge.isActive();
    }

    // 도전 가능한 챌린지
    public boolean canUserParticipateInChallenge(Long memberNum, Long challengeId) {
        Member member = memberRepository.findById(memberNum).orElse(null);
        if (member == null) {
            return false;
        }
        
        // 사용자가 현재 활성화된 챌린지를 가지고 있는지 확인
        boolean hasActiveChallenge = userChallengeRepo.existsByMemberAndActiveTrue(member);
        if (hasActiveChallenge) {
            return false;
        }
        
        // 특정 챌린지에 이미 참여했는지 확인
        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        if (challenge == null) {
            return false;
        }
        
        return !userChallengeRepo.existsByMemberAndChallenge(member, challenge);
    }


    // 챌린지 시작하기
    public UserChallenge startChallenge(String username, Long challengeId) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(() -> new DataNotFoundException("챌린지를 찾을 수 없습니다."));

        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setActive(true);
        userChallenge.setMember(member);
        userChallenge.setChallenge(challenge);
        userChallenge.setStartDate(LocalDateTime.now());
        userChallenge.setCompleted(false);

        return userChallengeRepo.save(userChallenge);
    }
}
