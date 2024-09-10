package com.test.lms.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.createform.MemberCreateForm;
import com.test.lms.entity.Exp;
import com.test.lms.entity.Member;
import com.test.lms.entity.dto.PasswordChangeRequest;
import com.test.lms.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class RestMemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        log.info("Signup request received for email: {}", memberCreateForm.getEmail());
        
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred during signup");
            return ResponseEntity.badRequest()
                                .body(Map.of("error", "입력값이 올바르지 않습니다."));
        }

        try {
            memberService.create(memberCreateForm.getUsername(),
                                memberCreateForm.getPassword(),
                                memberCreateForm.getNickname(),
                                memberCreateForm.getEmail());
            log.info("User successfully signed up: {}", memberCreateForm.getUsername());
            return ResponseEntity.ok()
                                .body(Map.of("message", "회원가입이 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            log.error("Error during user creation: {}", e.getMessage());
            return ResponseEntity.badRequest()
                                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error during signup", e);
            return ResponseEntity.internalServerError()
                                .body(Map.of("error", "회원가입 처리 중 오류가 발생했습니다."));
        }
    }
    
    @GetMapping("/check")
    public Map<String, Object> checkLoginStatus() {
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findByUsername(authentication.getName());
        if (authentication != null && authentication.isAuthenticated()) {
            response.put("loggedIn", true);
            response.put("username", authentication.getName()); // 사용자 이름을 반환
            response.put("nickname", member.getNickname());
            response.put("role", member.getRole());
        } else {
            response.put("loggedIn", false);
        }

        return response;
    }
    
    @GetMapping("/mypage")
    public ResponseEntity<?> getMemberDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        Member member = memberService.findByUsername(username);
        Exp exp = memberService.findExpByMember(member);
        
        Map<String, Object> response = new HashMap<>();
        response.put("username", member.getUsername());
        response.put("nickname", member.getNickname());
        response.put("email", member.getEmail());
        response.put("rank", member.getUserRank());
        response.put("expPoints", exp.getExpPoints());

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsernameDuplicate(@RequestParam("username") String username) {
        boolean isDuplicate = memberService.existsByUsername(username);
        
        if (isDuplicate) {
            return ResponseEntity.badRequest().body(Map.of("error", "이미 사용 중인 아이디입니다."));
        } else {
            return ResponseEntity.ok().body(Map.of("message", "사용 가능한 아이디입니다."));
        }
    }
    
    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNicknameDuplicate(@RequestParam("nickname") String nickname) {
        boolean isDuplicate = memberService.existsByNickname(nickname);
        
        if (isDuplicate) {
            return ResponseEntity.badRequest().body(Map.of("error", "이미 사용 중인 닉네임입니다."));
        } else {
            return ResponseEntity.ok().body(Map.of("message", "사용 가능한 닉네임입니다."));
        }
    }
    
    // 비밀번호 변경 
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        memberService.changePassword(request.getUsername(), request.getNewPassword(), request.getConfirmPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

}