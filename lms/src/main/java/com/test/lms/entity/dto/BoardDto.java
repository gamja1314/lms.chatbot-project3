package com.test.lms.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	
	private String title;
    private String content;
    
    @NotNull
    private Long memberNum; 
    
}
