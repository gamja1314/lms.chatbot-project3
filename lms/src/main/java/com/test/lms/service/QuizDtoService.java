package com.test.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Quiz;
import com.test.lms.entity.dto.QuizDto;
import com.test.lms.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizDtoService {

    private final QuizRepository quizRepository;

    public Page<QuizDto> getList(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Quiz> quizPage = quizRepository.findAll(pageRequest);
        
        return quizPage.map(QuizDto::fromEntity);
    }
}
