package com.test.lms.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.createform.MemberCreateForm;
import com.test.lms.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RestMemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        log.info("Signup request received for email: {}", memberCreateForm.getEmail());
        
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred during signup");
            return ResponseEntity.badRequest().body("입력값이 올바르지 않습니다.");
        }

        try {
            memberService.create(memberCreateForm.getUsername(),
                                memberCreateForm.getPassword(),
                                memberCreateForm.getNickname(),
                                memberCreateForm.getEmail());
            log.info("User successfully signed up: {}", memberCreateForm.getUsername());
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            log.error("Error during user creation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during signup", e);
            return ResponseEntity.internalServerError().body("회원가입 처리 중 오류가 발생했습니다.");
        }
    }
}