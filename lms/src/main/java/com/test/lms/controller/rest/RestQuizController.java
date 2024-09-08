package com.test.lms.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.Quiz;
import com.test.lms.service.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class RestQuizController {

    private final QuizService quizService;

    // 퀴즈 목록 API (페이징 처리)
    @GetMapping("/list")
    public ResponseEntity<Page<Quiz>> getQuizList(@RequestParam(value = "page", defaultValue = "0") int page) {
        // 페이징 처리된 퀴즈 목록을 JSON 형태로 반환
        Page<Quiz> paging = quizService.getList(page);
        return ResponseEntity.ok(paging);
    }

    // 퀴즈 상세 정보 API
    @GetMapping("/detail/{quizId}")
    public ResponseEntity<Quiz> getQuizDetail(@PathVariable("quizId") Long quizId) {
        // 퀴즈 ID로 퀴즈 상세 정보 가져오기
        Quiz quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    // 퀴즈 정답 제출 및 검증 API
    @PostMapping("/submit")
    public ResponseEntity<String> submitQuizAnswer(
        @RequestParam Long quizId,  // 퀴즈 ID
        @RequestParam String answer,  // 사용자가 제출한 답변
        @RequestParam boolean isPublic,  // 정답 공개 여부
        @RequestParam Long memberId  // 사용자 ID
    ) {
        // 퀴즈 정답 제출 및 결과 메시지 반환
        String resultMessage = quizService.submitQuizAnswer(quizId, answer, isPublic, memberId);

        // 결과 메시지를 JSON 형식으로 반환
        return ResponseEntity.ok(resultMessage);
    }
}
