package com.test.lms.controller.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.Notice;
import com.test.lms.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class RestNoticeController {

    private final NoticeService noticeService;

    // //모든 공지사항 조회
    // @GetMapping
    // public ResponseEntity<List<Notice>> getAllNotices() {
    //     List<Notice> notices = noticeService.getAllNotice();
    //     return ResponseEntity.ok(notices);
    // }

    // 페이징 처리된 공지사항 리스트
    // @GetMapping("/page/{page}")
    public ResponseEntity<Page<Notice>> getPagedNotices(@PathVariable int page) {
        Page<Notice> pagedNotices = noticeService.getList(page);
        return ResponseEntity.ok(pagedNotices);
    }

    //공지사항 작성(관리자만!)
    @PostMapping("/create")
    public ResponseEntity<Notice> createNotice(@RequestParam String title, @RequestParam String content, @RequestParam String username){
        
        Notice newNotice = noticeService.create(title, content, username);

        return ResponseEntity.ok(newNotice);
    }

    //공지사항 수정(관리자만!)
    @PutMapping("/update/{noticeId}")
    public ResponseEntity<Notice> updateNotice(@PathVariable("noticeId") Long noticeId, String title, String content){

        Notice notice = new Notice();

        notice.setNoticeId(noticeId);
        notice.setTitle(title);
        notice.setContent(content);
        Notice updatedNotice = noticeService.updateNotice(notice);
        return ResponseEntity.ok(updatedNotice);
    }

    // 공지사항 삭제 (관리자만 접근 가능)
    @DeleteMapping("/delete/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        Notice notice = new Notice();
        notice.setNoticeId(noticeId);
        noticeService.delete(notice);
        return ResponseEntity.ok("삭제 되었습니다.");
    }

    //공지사항 검색
    // @GetMapping("/search")
    // public ResponseEntity<List<Notice>> searchNotice(@RequestParam String keyword){

    // }
 
}
