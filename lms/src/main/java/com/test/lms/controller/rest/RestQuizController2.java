package com.test.lms.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.dto.QuizDto;
import com.test.lms.service.QuizDtoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class RestQuizController2 {

    private final QuizDtoService quizService;
    
    @GetMapping("/list")
    public ResponseEntity<Page<QuizDto>> quizBoard(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<QuizDto> paging = this.quizService.getList(page);
        return ResponseEntity.ok(paging);
    }
    
}
