package com.test.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.lms.entity.ChatHistory;
import com.test.lms.entity.Member;
import com.test.lms.entity.Quiz;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
	
	//@Query("SELECT ch FROM ChatHistory ch WHERE ch.member = :member AND ch.quiz = :quiz")
    Optional<ChatHistory> findByMemberAndQuiz(Member member, Quiz quiz);
}
