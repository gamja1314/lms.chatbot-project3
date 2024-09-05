package com.test.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.lms.entity.QuizAnswer;
import com.test.lms.repository.QuizAnswerRepository;

@Service
public class QuizAnswerService {

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    public List<QuizAnswer> getQuizAnswer(Long quizId){

        List<QuizAnswer> quizAnswers = quizAnswerRepository.findByQuizId(quizId);

        return quizAnswers;
    }    

    public void submitQuizAnswer(Long quizId, String correct, boolean isPublic, Long Id){

    }
}
