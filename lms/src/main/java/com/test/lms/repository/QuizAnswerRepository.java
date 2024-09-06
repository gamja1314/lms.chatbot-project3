package com.test.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Quiz;
import com.test.lms.entity.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

    List<QuizAnswer> findByQuiz(Quiz quiz);
  
    List<QuizAnswer> findByQuiz_Id(Long quizId);
}
