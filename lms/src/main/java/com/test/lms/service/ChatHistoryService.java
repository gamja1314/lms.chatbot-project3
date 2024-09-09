package com.test.lms.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.lms.entity.ChatHistory;
import com.test.lms.entity.Member;
import com.test.lms.entity.Quiz;
import com.test.lms.repository.ChatHistoryRepository;
import com.test.lms.repository.MemberRepository;
import com.test.lms.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {

	private final MemberRepository memberRepository;
	private final QuizRepository quizRepository;
	private final ChatHistoryRepository chatHistoryRepository;
	
	//대화내역 생성
	public void ChatHistoryCreate(Member member, Quiz quiz, String memberContent, String botContent) {
		
		ChatHistory chatHistory = new ChatHistory();
		
		chatHistory.setMember(member);
		chatHistory.setQuiz(quiz);
		chatHistory.setBotContent(botContent);
		chatHistory.setMemberContent(memberContent);
		chatHistory.setCreateTime(LocalDateTime.now());
		
		this.chatHistoryRepository.save(chatHistory);
		
	}
	
	//대화내역 조회
	public ChatHistory findChatHistory(Member member, Quiz quiz) {
		
		Optional<ChatHistory> chatHistory = chatHistoryRepository.findByMemberAndQuiz(member, quiz);
		
		return chatHistory.get();
	}
	
	//대화내역 수정
	public void ChatHistoryUpdate(Member member, Quiz quiz, String memberContent, String botContent) {
		
		ChatHistory chatHistory = new ChatHistory();
		
		chatHistory.setMember(member);
		chatHistory.setQuiz(quiz);
		chatHistory.setBotContent(botContent);
		chatHistory.setMemberContent(memberContent);
		chatHistory.setCreateTime(LocalDateTime.now());
		
		this.chatHistoryRepository.save(chatHistory);
		
	}
	
	//대화내역 삭제
	public void ChatHistoryDelete(ChatHistory chatHistory) {
		this.chatHistoryRepository.delete(chatHistory);
	}
	
	
	
}
