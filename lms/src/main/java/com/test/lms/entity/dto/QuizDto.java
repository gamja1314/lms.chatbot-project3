package com.test.lms.entity.dto;

import java.util.Random;

import com.test.lms.entity.Quiz;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDto {
    // 퀴즈 리스트에서 보여줄 정보들

    private Long quizId;
    private String title;
    private String quizRank;
    // 맞춘 인원
    private int solvedBy;
    // 정답률
    private String correctRate;

    public static QuizDto fromEntity(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setQuizId(quiz.getQuizId());
        dto.setTitle(quiz.getTitle());
        dto.setQuizRank(quiz.getQuizRank());
        
        // 임시 데이터 생성
        Random random = new Random();
        dto.setSolvedBy(random.nextInt(2000) + 1);  // 1 ~ 2000
        dto.setCorrectRate(random.nextInt(100) + "%");  // 0% ~ 99%

        return dto;
    }
}
