package com.test.lms.entity;

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
public class Quiz {

    //PK
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long quizId;

    //퀴즈 내용
    @Column(nullable=false)
    private String title;

    //퀴즈의 정답
    @Column(nullable=false)
    private String correct;

    //카테고리와 매핑
    @ManyToOne
    @JoinColumn(name = "id")
    private Category id;

    //출력예시
    public String quizAnswer(){
        return "문제 : " + title + "정답 : " + correct;
    }
}
