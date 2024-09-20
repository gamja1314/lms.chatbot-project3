package com.test.lms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Board;
import com.test.lms.entity.dto.BoardDto;
import com.test.lms.repository.BoardRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
    // 게시글 하나 가져오기
    public Board getOne(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다. ID : " + id));
    }
    
    //모든 게시글 가져오기
    public List<Board> getAllBoard(){
        return boardRepository.findAll();
    }

     //페이징 처리
    public Page<Board> getList(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.boardRepository.findAll(pageable);
    }

    //게시글 작성
    public Board create(BoardDto BoardDto) {
        Board Board = new Board();
        Board.setTitle(BoardDto.getTitle());
        Board.setContent(BoardDto.getContent());
        Board.setCreateDate(LocalDateTime.now());

        return boardRepository.save(Board);
    }

    //게시글 수정
    public Board updateBoard(Long id, BoardDto BoardDto){

        Board existingBoard = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다. ID : " + id));
        existingBoard.setTitle(BoardDto.getTitle());
        existingBoard.setContent(BoardDto.getContent());

        return boardRepository.save(existingBoard);
    }

    //게시글 삭제
    public void delete(Long id){
        Board Board = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다. ID : " + id));
        this.boardRepository.delete(Board);
    }
    
    // 게시글 추천 수 증가
    public Board likeBoard(Long boardId) {
    	
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다. ID : " + boardId));

        // 추천 수 증가
        board.setLikeCount(board.getLikeCount() + 1);

        return boardRepository.save(board);
    }
    
//  // 최근 등록된 챌린지 10개 조회
//  public List<Board> get10Boards() {
//      Pageable pageable = PageRequest.of(0, 10);
//      return boardRepository.findRecent5(pageable);
//  }

}
