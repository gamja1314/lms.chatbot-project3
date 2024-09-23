package com.test.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.lms.entity.Challenge;
import com.test.lms.entity.Member;
import com.test.lms.entity.UserChallenge;


public interface UserChallengeRepo extends JpaRepository<UserChallenge, Long> {

    Optional<UserChallenge> findByMemberAndChallenge(Member member, Challenge challenge);

    boolean existsByMemberAndActiveTrue(Member member);

    boolean existsByMemberAndChallenge(Member member, Challenge challenge);
    
}
