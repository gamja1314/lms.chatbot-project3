package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long noticeId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @ManyToOne //한 작성자가 여러 공지 작성
    @JoinColumn(name="username")
    private Member author;  //작성자

    @Column(nullable=false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate; //수정일자

    


}
