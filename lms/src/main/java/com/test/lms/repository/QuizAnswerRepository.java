package com.test.lms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.lms.entity.Member;
import com.test.lms.entity.Quiz;
import com.test.lms.entity.QuizAnswer;


public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

    List<QuizAnswer> findByQuiz(Quiz quiz);
    List<QuizAnswer> findByMember(Member member);

    @Query("SELECT DISTINCT qa.quiz FROM QuizAnswer qa WHERE qa.isCorrect = true")
    Page<Quiz> findDistinctQuizzesByCorrectAnswers(Pageable pageable);
    
    @Query("SELECT DISTINCT q FROM QuizAnswer q WHERE q.solvedQuizTime BETWEEN :start AND :end GROUP BY q.id, q.solvedQuizTime ORDER BY q.solvedQuizTime DESC")
    List<QuizAnswer> findTop5DistinctBySolvedQuizTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    // 특정 퀴즈에 대해 정답으로 제출된 횟수 조회
    long countByQuizAndOutput(Quiz quiz, String output);
}
