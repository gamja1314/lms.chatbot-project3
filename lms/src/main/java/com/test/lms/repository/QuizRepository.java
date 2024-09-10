package com.test.lms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.lms.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    //페이징 처리
    Page<Quiz> findAll(Pageable pageable);

    @Query("SELECT q FROM Quiz q ORDER BY q.count DESC")
    List<Quiz> findTop5QuizzesByCountDesc(Pageable pageable);
}
