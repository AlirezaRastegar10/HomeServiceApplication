package com.example.service;

import com.example.dto.expert.*;
import com.example.entity.Expert;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpertService {

    Expert findById(Long id);
    GetModifiedExpertTimeDto changePassword(Expert expert);
    List<Expert> findAllNewExperts();
    GetModifiedExpertTimeDto confirmExpert(Long id);
    void addExpertToSubService(Expert expert);
    void removeExpertFromSubService(Expert expert);
    void saveExpertImage() throws IOException;
    void calculateExpertScore(Expert expert, LocalDateTime localDateTime);
    void updateExpertCredit(Long money, Expert expert);
    void updateExpertScore(Integer score, Expert expert);
    Expert verifyExpert(String token);
    Expert showScore();
    List<Expert> search(Specification<Expert> spec);
    Expert showCredit();
}
