package com.test.lms.repository;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query("SELECT q FROM QuizAnswer q WHERE q.solvedQuizTime BETWEEN :start AND :end ORDER BY q.solvedQuizTime DESC")
    List<QuizAnswer> findTop5BySolvedQuizTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    
}
