package com.test.lms.entity;

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
    private String quiz;

    //정답
    private String answer;

    //출력예시
    public String quizAnswer(){
        return "문제 : " + quiz + "정답 : " + answer;
    }
}
