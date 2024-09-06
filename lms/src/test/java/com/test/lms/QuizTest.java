package com.test.lms;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.lms.entity.Quiz;
import com.test.lms.repository.QuizRepository;
import com.test.lms.service.QuizService;


@SpringBootTest
public class QuizTest {

    @Autowired
    private QuizService quizService;
    // private QuizRepository quizRepository;
    

    @Test
    void quizTest(){

        Quiz q1 = new Quiz();
        
        String quizRank = "D";
        String title = "자바 웹 페이지 개발툴의 이름은?";
        String correct = "Spring Boot";
        quizService.create(title, correct, quizRank);

    }
}
