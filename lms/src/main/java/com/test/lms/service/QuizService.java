package com.test.lms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        //모든 퀴즈 리스트 가져오기
        public List<Quiz> getAllQuizzes(){
                return quizRepository.findAll();
        }

        //퀴즈 디테일 가져오기
        public Quiz getQuizById(Long quizId){
                return quizRepository.findById(quizId).orElseThrow(()->new EntityNotFoundException("퀴즈를 찾을 수 없습니다! ID : " + quizId));
        }     

        //퀴즈 생성
        public Quiz create(String title, String content, String correct, String quizRank){

                Quiz quiz = new Quiz();

                quiz.setTitle(title);
                quiz.setContent(content);
                quiz.setCorrect(correct);
                quiz.setQuizRank(quizRank);
                quiz.setCreateDate(LocalDateTime.now());

                return quizRepository.save(quiz);
        }

        //퀴즈 삭제
        public void delete(Quiz quiz){
                this.quizRepository.delete(quiz);
        }

        //퀴즈 수정

        public Quiz updateQuiz(Quiz quiz){
                
                if(quiz == null || quiz.getQuizId() == 0L ) {
                throw  new IllegalArgumentException("존재하지 않는 퀴즈 정보입니다.");
                }

                //기존레슨 정보 DB에서 조회
                Quiz existingQuiz = quizRepository.findById(quiz.getQuizId()).orElseThrow(() -> new EntityNotFoundException("해당 퀴즈를 찾을 수 없습니다. ID :" + quiz.getQuizId()));

                existingQuiz.setTitle(quiz.getTitle()); //제목 수정
                existingQuiz.setContent(quiz.getContent()); //내용 수정
                existingQuiz.setCorrect(quiz.getCorrect()); //정답 수정
                existingQuiz.setQuizRank(quiz.getQuizRank()); //랭크 수정

                return quizRepository.save(existingQuiz);
        }

        //페이징 처리
        public Page<Quiz> getList(int page){
                Pageable pageable = PageRequest.of(page,10);
                return this.quizRepository.findAll(pageable);
        }


        //퀴즈 정답
        public String submitQuizAnswer(Long quizId, String answer, boolean isPublic, Long Id){

                //quiz ID로 퀴즈 정보 가져오기
                Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("퀴즈를 찾을 수 없습니다."));

                //사용자 ID로 멤버 정보 가져오기
                Member member = memberRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
                
                QuizAnswer quizAnswer = new QuizAnswer();
                quizAnswer.setAnswer(answer);
                quizAnswer.setPublic(isPublic);
                quizAnswer.setMember(member);
                quizAnswer.setQuiz(quiz);

                //제출된 답과 퀴즈의 정답을 비교
                boolean isCorrect = quiz.getCorrect().equalsIgnoreCase(answer);

                // DB에 저장
                quizAnswerRepository.save(quizAnswer);
                
                //정딥 비교 결과 화면
                return isCorrect ? "정답입니다!" : "오답입니다. 다시 시도하세요.";
        }
        

        //개발 진행에 따라 필요하면 복구 또는 삭제

        // public Quiz getQuizAnswer(Long quizId, Long Id){
                
        //         //퀴즈 ID로 퀴즈 찾기
        //         Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("퀴즈를 찾을 수 없습니다."));
        //         //사용자 ID로 멤버 정보 가죠오기
        //         Member member = memberRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
        //         QuizAnswer quizAnswer = quizAnswerRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException());
        //         // 정답이 비공개이면 정답을 숨겨줌
        //         if (!quizAnswer.isPublic() && !quizAnswer.getMember().getMemberNum().equals(member.getMemberNum())){
        //                 quizAnswer.setAnswer(null);
        //         }
                
        //         //퀴즈는 항상 반환, 정답은 조건에 따라 다름
        //         return quiz;
        // }
}
