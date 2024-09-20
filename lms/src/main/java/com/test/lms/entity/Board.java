package com.test.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long boardId; // 게시글 번호
	
	@Column(nullable = false, length = 50)
	private String title; // 제목
	
	@Column(columnDefinition = "LONGTEXT", nullable = false)
	private String content; // 내용
	
	@Column(nullable = false) 
	private LocalDateTime createDate; // 작성일 
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "member_id", nullable=false) 
	private Member member; // 작성자
	
	private int likeCount = 0; // 추천수
}
