package com.test.lms.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.service.QuizAnswerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizAnswer")
public class RestQuizAnswrController {

    private final QuizAnswerService quizAnswerService;
    

    
}
