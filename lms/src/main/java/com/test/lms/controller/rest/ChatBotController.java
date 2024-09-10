package com.test.lms.controller.rest;
import java.util.Map;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.Member;
import com.test.lms.entity.Quiz;
import com.test.lms.service.ChatHistoryService;
import com.test.lms.service.MemberService;
import com.test.lms.service.QuizService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatBotController {
	
	private final AnthropicChatModel chatModel;
	private final QuizService quizService;
	private final ChatHistoryService chatHistoryService;
	private final MemberService memberService;

	//챗봇기능
	@GetMapping("/ai/generate")
	public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message,
			@RequestParam(value = "quizId") Long quizId) {
		//역할 및 기능 지정
		String systemPrompt = "너는 코딩문제를 푸는 사람을 도와주는 친구야." 
			+ "절대 정답을 말하지 않고 질문이 들어오는 걸 토대로 문제가 해결될 수 있도록 도와줘." 
			+ "멘트는 대화형식이고 3줄이하로 작성해." 
			+ "대화는 **로 감싸"
			+ "마지막 멘트는 문제를 잘 풀 수 있게하는 격려의 말로 마무리해."
			+ "문제를 줄꺼고 뒤에 질문을 적을꺼야." 
			+ "문제풀이와 관련 없는 질문이라고 판단되면 문제에 관련된 질문을 하라고 말해줘."
			+ "인사말은 제외하고 말해줘."
			+ "너의 이름은 '유미'야"
			+ "냥냥체로 말해"
			+ "중간중간 '유미가~'같이 3인칭체를 사용해봐."
			+ "대답 시작을 '유미가 ~ '로 시작하지 말고 바로 본론으로 들어가"
			+ "대화 중에 당신의 행동을 묘사해야 합니다. 행동 묘사는 한 줄 띄우고 *로 감싸세요."
			+ "행동묘사를 하면 그에 어울리는 이모지도 넣어.";
		
		//문제
		//quizId로 Quiz 객체 조회
        Quiz quiz = quizService.getQuizById(quizId);
        
        if (quiz == null) {
            return Map.of("error", "Quiz not found");
        }
		
		log.info("Quiz 내용 : "+quiz.getContent());
		log.info("물어본질문 : "+ message);
		
		//로그인중인 회원
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findByUsername(authentication.getName());
		// Member member = null;
	    // if (authentication != null) {
		// 	log.info("로그인중인 회원 : "+authentication.getName());
	    //     member = memberService.findByUsername(authentication.getName());
	    // }
	    
	    if (member == null) {
	        return Map.of("error", "User not authenticated");
	    }
		
		//API로 보낼 회원의 질문(시스템프롬프트 + 퀴즈내용 + 회원의 질문)
		String question = systemPrompt+ quiz.getContent() +message;
		log.info("API로 보낼 질문 : "+question);
		
		//채팅내역저장
		log.info("챗봇 답변 : "+chatModel.call(question));
		try {
		    chatHistoryService.ChatHistoryCreate(member, quiz, message, chatModel.call(question));
		    log.info("채팅내역저장완료");
		} catch (Exception e) {
		    log.error("채팅내역 저장 중 오류 발생: ", e);
		}
		
		
		return Map.of("generation", chatModel.call(question));
	}//간단하고 즉각적인 응답에 적합(동기)

}