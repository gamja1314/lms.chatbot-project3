package com.test.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Exp;
import com.test.lms.entity.Member;

public interface ExpRepository extends JpaRepository<Exp, Long>{
    Optional<Exp> findByMember(Member member);
}
