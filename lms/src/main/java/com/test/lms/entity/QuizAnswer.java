package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성한 코드
    private String answer;
    // 제출한 답안
    private String output;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false, name = "is_public")
    private boolean isPublic;

    //문제를 푼 날짜를 기록 : 메인 페이지 일일퀴즈 랭킹 표기에 사용
    @Column(nullable = false)
    private LocalDateTime solvedQuizTime;

}