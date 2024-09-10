package com.test.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Notice;
import com.test.lms.entity.Quiz;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    //페이징 처리
    Page<Notice> findAll(Pageable pageable);
}
