package com.test.lms.entity;

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
public class BoardLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeId;
	
	@ManyToOne
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
}
