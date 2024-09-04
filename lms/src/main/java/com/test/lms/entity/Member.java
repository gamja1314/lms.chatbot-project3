package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor 
public class Member {
    
    // 테이블 :[ 멤버(관리자role) 퀴즈(코드 문제풀이 시 : ),  ], controller 기능 구현 x, service클래스 부터 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNum;

    private String memberId;

    private String pwd;

    private LocalDateTime birth;
    
    private String phoneNum;

    private String memberName;

    private String email;

    private String gender;

    private String addr;

    private LocalDateTime createTime;

}
