package com.test.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizAnswer {

    //PK
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //여러개의 답변을 하나의 퀴즈에 연결
    @ManyToOne
    private Quiz quiz;

    //답변을 저장하는 테이블
    private String answerSave;

    //회원이 제출한 답변을 문제와 함께 표시
    public String showQuizAnswer(){
        return "문제 : " + quiz.getQuiz() + "정답 : " + answerSave;
    }

}
