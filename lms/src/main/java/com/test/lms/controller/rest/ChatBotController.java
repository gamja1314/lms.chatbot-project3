 package com.test.lms.controller.rest;
 import java.util.Map;
 import org.springframework.ai.anthropic.AnthropicChatModel;
 import org.springframework.ai.chat.messages.UserMessage;
 import org.springframework.ai.chat.model.ChatResponse;
 import org.springframework.ai.chat.prompt.Prompt;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 import reactor.core.publisher.Flux;
 @RestController
 @RequiredArgsConstructor
 @Slf4j
 public class ChatBotController {
	 
     private final AnthropicChatModel chatModel;
     
     @GetMapping("/ai/generate")
     public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
    	 //역할 및 기능 지정
    	 String systemPrompt = "너는 코딩문제를 푸는 사람을 도와주는 친구야. 절대 정답을 말하지 않고 질문이 들어오는 걸 토대로 문제가 해결될 수 있도록 도와줘. 멘트는 대화형식이고 3줄이하로 작성해. 마지막 멘트는 문제를 잘 풀 수 있게하는 격려의 말로 마무리해. 문제를 줄꺼고 뒤에 질문을 적을꺼야. 문제풀이와 관련 없는 질문이라고 판단되면 문제에 관련된 질문을 하라고 말해줘.";
    	 //문제
    	 String Quiz = "문제: 가장 긴 팰린드롬 부분 문자열 찾기\r\n"
    	 		+ "설명:\r\n"
    	 		+ "주어진 문자열에서 가장 긴 팰린드롬(palindrome) 부분 문자열을 찾는 함수를 구현하세요. 팰린드롬이란 앞에서부터 읽으나 뒤에서부터 읽으나 같은 문자열을 말합니다.\r\n"
    	 		+ "요구사항:\r\n"
    	 		+ "\r\n"
    	 		+ "함수 이름: longest_palindrome_substring\r\n"
    	 		+ "입력: 문자열 s (1 ≤ s의 길이 ≤ 1000)\r\n"
    	 		+ "출력: 가장 긴 팰린드롬 부분 문자열\r\n"
    	 		+ "시간 복잡도: O(n^2) 이하 (n은 문자열의 길이)\r\n"
    	 		+ "\r\n"
    	 		+ "예시:\r\n"
    	 		+ "\r\n"
    	 		+ "입력: \"babad\"\r\n"
    	 		+ "출력: \"bab\" 또는 \"aba\"\r\n"
    	 		+ "입력: \"cbbd\"\r\n"
    	 		+ "출력: \"bb\"\r\n"
    	 		+ "입력: \"a\"\r\n"
    	 		+ "출력: \"a\"\r\n"
    	 		+ "입력: \"ac\"\r\n"
    	 		+ "출력: \"a\" 또는 \"c\"" ; 
    	 log.info("QUiz"+Quiz);
         log.info("물어본질문"+message);
         message = systemPrompt+Quiz+message;
         return Map.of("generation", chatModel.call(message));
     }//간단하고 즉각적인 응답에 적합(동기)
    /* @GetMapping("/ai/generateStream")
     public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
         Prompt prompt = new Prompt(new UserMessage(message));
         log.info(message);*/
         // return chatModel.stream(prompt);
     //}//대화형 AI나 긴 텍스트 생성과 같이 점진적인 응답이 필요한 경우에 더 적합(비동기)
 }