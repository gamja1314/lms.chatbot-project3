package com.test.lms.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.QuizAnswer;
import com.test.lms.service.QuizAnswerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizAnswer")
public class RestQuizAnswrController {

    private final QuizAnswerService quizAnswerService;
    
    //특정 퀴즈에 대한 QuizAnswer 목록을 JSON 으로 반환
    @GetMapping("/api/quiz/{quizId}/answers")
    public ResponseEntity<List<QuizAnswer>> getQuizAnswer(@PathVariable("quizId") Long quizId){

        //QuizAnswer 목록 조회
        List<QuizAnswer> quizAnswers = quizAnswerService.getQuizAnswer((quizId));

        //JSON 으로 QuizAnswer 목록 반환
        return ResponseEntity.ok(quizAnswers);
    }
    
}
