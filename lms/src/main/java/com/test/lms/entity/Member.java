package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    
    
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long memberNum; // 회원번호


    @Column(unique = true, length = 30)
    private String username;    // 아이디

    @Column(nullable = false, length = 100)
    private String password;    // 비밀번호

    @Column(unique = true, length = 30)
    private String nickname;    // 닉네임

    @Column(nullable = false, length = 30)
    private String email;   // 이메일


    private String memberRank;

    @Column(length = 20)
    private String userRank;    // 랭크


    @Column(length = 20)
    private String role;    // 권한

    private LocalDateTime createTime;   // 가입날짜

}
