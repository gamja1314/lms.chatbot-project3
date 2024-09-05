package com.test.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
