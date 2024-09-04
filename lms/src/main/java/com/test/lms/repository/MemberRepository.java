package com.test.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
