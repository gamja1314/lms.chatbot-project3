package com.test.lms.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.lms.entity.Quiz;
import com.test.lms.service.QuizAnswerService;
import com.test.lms.service.QuizService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    
    private final QuizService quizService;
    private final QuizAnswerService quizAnswerService;

    //퀴즈 리스트 보기
    @GetMapping("/quizBoard")
    public String quizBoard(Model model, @RequestParam(value="page", defaultValue="0")int page){
        //모든 퀴즈 리스트를 가져옴
        Page<Quiz> paging = this.quizService.getList(page);
        model.addAttribute("paging", paging);
        return "quiz/quizBoard";
        // model.addAttribute("quizBoard", quizService.getAllQuizzes());
        // return "quiz/quizBoard";
    }

    //퀴즈 상세내역
    @GetMapping("/quizDetail/{quizId}")
    public String quizDetail(@PathVariable("quizId") Long quizId, Model model){
        
        model.addAttribute("quizDetail", quizService.getQuizById(quizId));
        return "quiz/quizDetail";
          
    }


    @PostMapping("/submit")
    public ResponseEntity<String> submitQuiz(@RequestParam Long quizId, @RequestParam String correct, @RequestParam boolean isPublic, @RequestParam Long Id){ 
        
        //제출 시 정답 공개 여부 설정
        quizService.submitAnswer(quizId, correct, isPublic, Id);
        return ResponseEntity.ok("정답 제출 완료");
    }

    //퀴즈 정답 조회
    @GetMapping("/view/{quizId}")
    public String viewQuizAnswer(@PathVariable Long quizId, Model Model){

        Model.addAttribute("quizAnswer", quizAnswerService.getQuizAnswer(quizId));

        //랜더링할 HTML파일 이름
        return "quizAnswerView";
    }
}
