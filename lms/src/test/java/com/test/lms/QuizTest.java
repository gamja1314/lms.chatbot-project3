package com.test.lms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.lms.entity.Quiz;
import com.test.lms.service.QuizService;

import io.swagger.v3.oas.annotations.media.Content;


@SpringBootTest
public class QuizTest {

    @Autowired
    private QuizService quizService;
    // private QuizRepository quizRepository;
    

    @Test
    void quizTest(){

        Quiz q1 = new Quiz();
        
        String quizRank = "D";
        String title = "기초 문제";
        String content = "자바 웹페이지 개발툴의 이름은?";
        String correct = "Spring Boot";
        quizService.create(title, content, correct, quizRank);

    }
}
