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
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String answer;

    @Column(nullable = false)
    private boolean isPublic;

    public String showQuizAnswer() {
        return "문제 : " + quiz.getTitle() + " 정답 : " + answer;
    }
}