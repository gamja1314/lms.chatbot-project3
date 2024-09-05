package com.test.lms.entity;

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
public class QuizAnswer {

    //PK
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //퀴즈와 회원 각각 매핑
    // @OneToOne
    private Quiz quiz;
    // @OneToOne
    private Member member;

    //답변을 저장하는 테이블
    private String answer;

      //퀴즈 정답 공개 여부 (true:공개 false:비공개)
    @Column(nullable=false)
    private boolean isPublic;

        //회원이 제출한 답변을 문제와 함께 표시
    public String showQuizAnswer(){
        return "문제 : " + quiz.getTitle() + " 정답 : " + answer;
    }
}
