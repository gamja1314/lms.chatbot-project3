package com.test.lms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.lms.entity.Quiz;
import com.test.lms.entity.QuizAnswer;
import com.test.lms.repository.QuizAnswerRepository;
import com.test.lms.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizAnswerService {

	private final QuizRepository quizRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    public List<QuizAnswer> getQuizAnswer(Long quizId){

    	Quiz quiz = quizRepository.findById(quizId).orElse(null);
    	
        //quiz가 null이면 빈 리스트 반환
        if(quiz == null){
            return List.of();
        }

        // 퀴즈 답변 목록 조회
        return quizAnswerRepository.findByQuiz(quiz);

    }    

}
