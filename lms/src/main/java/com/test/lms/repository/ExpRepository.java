package com.test.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Exp;
import com.test.lms.entity.Member;

public interface ExpRepository extends JpaRepository<Exp, Long>{
	int sumExpPointsByMember(Member member);
	int findTotalExpByMember(Member member);
}
