package com.test.lms.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRankingDto {

    private String nickname; //멤버 닉네임
    private int expPoints; //멤버 경험치

    public MemberRankingDto(String nickname, int expPoints){
        this.nickname = nickname;
        this.expPoints = expPoints;
    }
}
