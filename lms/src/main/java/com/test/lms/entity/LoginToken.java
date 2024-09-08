package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class LoginToken {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String token;    

    @Column(nullable = false)
    private LocalDateTime lastUsed; 

    @ManyToOne
    @JoinColumn(name = "member_num", nullable = false)
    private Member member; 
}	
