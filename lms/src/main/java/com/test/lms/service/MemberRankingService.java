package com.test.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.lms.entity.Exp;
import com.test.lms.repository.ExpRepository;

@Service
public class MemberRankingService {

    @Autowired
    private ExpRepository expRepository;

    public List<Exp> getMemberRanking(){
        
        return expRepository.findAllOrderByExpPointsDesc();
    }
}
