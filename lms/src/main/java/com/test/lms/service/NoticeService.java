package com.test.lms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Notice;
import com.test.lms.entity.Member;
import com.test.lms.repository.MemberRepository;
import com.test.lms.repository.NoticeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    //모든 공지 가져오기
    public List<Notice> getAllNotice(){
        return noticeRepository.findAll();
    }

     //페이징 처리
    public Page<Notice> getList(int page){
        Pageable pageable = PageRequest.of(page,5);
        return this.noticeRepository.findAll(pageable);
    }

    //공지 작성
    public Notice create(String title, String content, String username){

        //username 으로 Member 엔티티 조회
        Member author = memberRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));

        Notice notice = new Notice();

        notice.setTitle(title);
        notice.setContent(content);
        notice.setAuthor(author);
        notice.setCreateDate(LocalDateTime.now());

        return noticeRepository.save(notice);
    }

    //공지 수정
    public Notice updateNotice(Notice notice){

        if (notice==null || notice.getNoticeId() == 0L){
            throw new IllegalArgumentException("공지가 존재하지 않습니다.");
        }

        Notice existingNotice = noticeRepository.findById(notice.getNoticeId()).orElseThrow(() -> new EntityNotFoundException("해당 공지를 찾을 수 없습니다. ID : " + notice.getNoticeId()));
        existingNotice.setTitle(notice.getTitle());
        existingNotice.setContent(notice.getContent());

        return noticeRepository.save(existingNotice);
    }

    //공지 삭제
    public void delete(Notice notice){
        this.noticeRepository.delete(notice);
    }
    
}
