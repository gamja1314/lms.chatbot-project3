package com.test.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.test.lms.entity.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

    List<QuizAnswer> findByQuiz_Id(Long quizId);
}
