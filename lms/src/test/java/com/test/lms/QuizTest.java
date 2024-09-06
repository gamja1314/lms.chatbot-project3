package com.test.lms;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.lms.entity.Quiz;
import com.test.lms.repository.QuizRepository;

@SpringBootTest
public class QuizTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    void quizTest(){

        Quiz q1 = new Quiz();
        
        q1.setTitle("기초 산수 문제 - 1 + 1 = ?");
        q1.setCorrect("창문");
        q1.setCreateDate(LocalDateTime.now());
        this.quizRepository.save(q1);

        Quiz q2 = new Quiz();

        q2.setTitle("기초 산수 문제 - 2 + 2 = ?");
        q2.setCorrect("4");
        q2.setCreateDate(LocalDateTime.now());
        this.quizRepository.save(q2);
    }
}
