package com.test.lms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.lms.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //페이징 처리
    Page<Board> findAll(Pageable pageable);

    @Query("SELECT n FROM Board n ORDER BY n.createDate DESC")
    List<Board> findRecent10(Pageable pageable);
}
