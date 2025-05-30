package com.test.lms.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable=false, columnDefinition = "LONGTEXT")
    private String content;

    private int expPoints;

    // 종료된 챌린지
    @Column(name = "is_close")
    private boolean close;

    private LocalDateTime createDate;

    @JsonBackReference
    @OneToMany(mappedBy = "challenge")
    private List<UserChallenge> userChallenges;
}
