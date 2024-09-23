package com.test.lms.controller.rest;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.lms.entity.Board;
import com.test.lms.entity.dto.BoardDto;
import com.test.lms.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
public class BoardController {


	private final BoardService boardService;
	
    // 단일 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<Board> getBoard(@PathVariable("boardId") Long id) {
        Board board = boardService.getOne(id);
        return ResponseEntity.ok(board);
    }

    // 페이징 처리된 게시글 리스트
    @GetMapping("/list")
    public ResponseEntity<Page<Board>> getPagedBoards(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Board> pagedBoards = boardService.getList(page);
        return ResponseEntity.ok(pagedBoards);
    }

    @PostMapping("/create")
    public ResponseEntity<Board> createBoard(@RequestBody @Valid BoardDto boardDto, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String username = authentication.getName();
        Board newBoard = boardService.create(boardDto, username);
        return ResponseEntity.ok(newBoard);
    }
    
    // 게시글 수정 (작성자 또는 관리자만)
    @PutMapping("/update/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto boardDto) {
        Board updatedBoard = boardService.updateBoard(boardId, boardDto);
        return ResponseEntity.ok(updatedBoard);
    }

    // 게시글 삭제 (작성자 또는 관리자만)
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

    // 게시글 추천 수 증가
    @PostMapping("/like/{boardId}")
    public ResponseEntity<Board> likeBoard(@PathVariable("boardId") Long boardId) {
        Board likedBoard = boardService.likeBoard(boardId);
        return ResponseEntity.ok(likedBoard);
    }
    
	//  // 최근 10개 게시글 가져오기
	//  @GetMapping
	//  public ResponseEntity<List<Board>> getRecentBoards() {
	//      List<Board> boards = boardService.getRecent10Boards();
	//      return ResponseEntity.ok(boards);
	//  }
}
