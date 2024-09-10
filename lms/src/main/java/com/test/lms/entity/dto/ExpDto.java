package com.test.lms.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpDto {
    
    private String nickname;
    private int expPoints;

    public ExpDto(String nickname, int expPoints) {
        this.nickname = nickname;
        this.expPoints = expPoints;
    }

}