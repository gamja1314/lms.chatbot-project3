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

    private QuizAnswerRepository quizAnswerRepository;
    private QuizRepository quizRepository;

    public List<QuizAnswer> getQuizAnswer(Long quizId) {

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        List<QuizAnswer> quizAnswers = quizAnswerRepository.findByQuiz(quiz);
      
        List<QuizAnswer> quizAnswers = quizAnswerRepository.findByQuiz_Id(quizId);

        return quizAnswers;
    }    

    public void submitQuizAnswer(Long quizId, String correct, boolean isPublic, Long Id){

    }
}
