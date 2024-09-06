package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quiz {

    //PK
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    //퀴즈 내용
    @Column(nullable=false)
    private String title;

    //퀴즈의 정답
    @Column(nullable=false)
    private String correct;

    @Column(nullable=false)
    private String rank;

    private LocalDateTime createDate;

    //출력예시
    public String quizAnswer(){
        return "문제 : " + title + "정답 : " + correct;
    }
}
