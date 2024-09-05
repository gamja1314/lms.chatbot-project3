package com.test.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.lms.createform.MemberCreateForm;
import com.test.lms.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
    
    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {
        return "member/signup";
    }

    // 회원가입 페이지
    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        memberService.create(memberCreateForm.getUsername(),
                             memberCreateForm.getPassword(),
                             memberCreateForm.getNickname(),
                             memberCreateForm.getEmail());

        return "redirect:/member/login";
    }
    
}
