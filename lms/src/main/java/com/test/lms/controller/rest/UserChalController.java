package com.test.lms.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.Member;
import com.test.lms.entity.UserChallenge;
import com.test.lms.service.MemberService;
import com.test.lms.service.UserChallengeService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/challenges-user")
@RequiredArgsConstructor
public class UserChalController {

    private final MemberService memberService;
    private final UserChallengeService userChallengeService;
    
    // 사용자의 챌린지 정보
    @GetMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> getChallengeStatus(@PathVariable("id") Long challengeId) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Member member = memberService.findByUsername(username);

        boolean isParticipating = false;
        boolean canParticipate = true;
        if (member != null) {
            isParticipating = userChallengeService.isUserParticipatingInChallenge(member.getMemberNum(), challengeId);
            canParticipate = userChallengeService.canUserParticipateInChallenge(member.getMemberNum(), challengeId);

        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("isParticipating", isParticipating);
        response.put("canParticipate", canParticipate);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserChallenge> startChallenge(@PathVariable("id") Long challengeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserChallenge challenge = userChallengeService.startChallenge(username, challengeId);
        
        return ResponseEntity.ok(challenge);
    }

}
