package com.test.lms.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.Exp;
import com.test.lms.entity.dto.MemberRankingDto;
import com.test.lms.service.MemberRankingService;

@RestController
public class RestMemberRankingController {

    @Autowired
    private MemberRankingService memberRankingService;

    
    @GetMapping("/api/member-ranking")
    public Map<String, Object> getMemberRanking() {

        // 전체 랭킹 리스트 가져오기
        List<Exp> rankingList = memberRankingService.getMemberRanking();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 로그인된 사용자의 이름(주로 username)

        int userRanking = -1;
        List<MemberRankingDto> rankingDtos = new ArrayList<>(); // DTO 리스트 초기화

        for (int i = 0; i < rankingList.size(); i++) {
            Exp exp = rankingList.get(i);
            MemberRankingDto dto = new MemberRankingDto(exp.getMember().getNickname(), exp.getExpPoints()); // DTO 생성

            rankingDtos.add(dto); // DTO 리스트에 추가

            // 여기서 username을 비교해야 하는지, nickname을 비교해야 하는지 확인 필요
            if (exp.getMember().getUsername().equals(currentUsername)) {
                userRanking = i + 1; // 1부터 랭킹 시작
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("ranking", rankingDtos);
        result.put("userRanking", userRanking);

        return result;
    }


}
