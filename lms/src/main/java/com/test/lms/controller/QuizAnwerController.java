package com.test.lms.controller;

import org.springframework.stereotype.Controller;

import com.test.lms.service.QuizAnswerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuizAnwerController {

    private final QuizAnswerService quizAnswerService;
}
