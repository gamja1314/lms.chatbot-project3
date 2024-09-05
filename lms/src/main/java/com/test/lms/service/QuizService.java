package com.test.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Member;
import com.test.lms.entity.Quiz;
import com.test.lms.entity.QuizAnswer;
import com.test.lms.repository.MemberRepository;
import com.test.lms.repository.QuizAnswerRepository;
import com.test.lms.repository.QuizRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

        private final QuizRepository quizRepository;
        private final MemberRepository memberRepository;
        private final QuizAnswerRepository quizAnswerRepository;

        //사용자가 퀴즈 정답 제출할때 호출
        public void submitAnswer(Long quizId, String correct, boolean isPublic, Long Id){

                //quiz ID로 정보를 가져옴, 없으면 예외 메시지 출력
                QuizAnswer quizAnswer = quizAnswerRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("퀴즈를 찾을 수 없습니다."));

                //사용자 ID로 멤버 정보 가져오기
                Member member = memberRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
                
                //정답 설정
                quizAnswer.setAnswer(correct);
                //공개 여부 설정
                quizAnswer.setPublic(isPublic);
                //퀴즈를 푼 멤버
                quizAnswer.setMember(member);

                //DB에 저장
                quizAnswerRepository.save(quizAnswer);

         }
        
        public Quiz getQuizAnswer(Long quizId, Long Id){
                
                //퀴즈 ID로 퀴즈 찾기
                Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("퀴즈를 찾을 수 없습니다."));
                //사용자 ID로 멤버 정보 가죠오기
                Member member = memberRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
                QuizAnswer quizAnswer = quizAnswerRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException());
                // 정답이 비공개이면 정답을 숨겨줌
                if (!quizAnswer.isPublic() && !quizAnswer.getMember().getId().equals(member.getId())){
                        quizAnswer.setAnswer(null);
                }
                
                //퀴즈는 항상 반환, 정답은 조건에 따라 다름
                return quiz;
        }
}
