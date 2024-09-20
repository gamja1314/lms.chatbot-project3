package com.test.lms;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.lms.service.MemberService;

@SpringBootTest
public class ShTest {

    @Autowired
    private MemberService memberService;

    // @Test
    // public void insertRandomMembers() {
    //     for(int i = 0; i<100; i++){
    //         String username = "user" + i;
    //         String password = UUID.randomUUID().toString();
    //         String nickname = "nickname" + i;
    //         String email = "email" + i + "@test.com";
        
    //     memberService.create(username, password, nickname, email);
    //     }
    // } 

    // @Test
    // public void testQuizAnswer(){

    //     Quiz q = new Quiz();
    //     q.setQuiz("현장맞춤 AI 자바 스프링부트 담당 강사 이름은?");
    //     q.setAnswer("최상배 강사님");

    //     QuizAnswer qa = new QuizAnswer();
    //     qa.setQuiz(q);
    //     qa.setAnswerSave("최상배 강사님");

    //     System.out.println(qa.showQuizAnswer());
    // }

    // @Test
    // public void noticeTest(){

    // }
}