package com.example.service.impl;


import com.example.dto.expert.*;
import com.example.entity.Expert;
import com.example.entity.SubService;
import com.example.entity.enumeration.ExpertStatus;
import com.example.exceptions.subservice.SubServiceExistException;
import com.example.exceptions.subservice.SubServiceNotFoundException;
import com.example.exceptions.user.PasswordNotMatchException;
import com.example.exceptions.user.UserExistException;
import com.example.exceptions.user.UserInActiveException;
import com.example.exceptions.user.UserNotFoundException;
import com.example.repository.ExpertRepository;
import com.example.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final SubServiceServiceImpl subServiceService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Expert findById(Long id) {
        return expertRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("no expert found with this ID: %s.", id)));
    }

    @Override
    public GetModifiedExpertTimeDto changePassword(Expert expert) {
        Expert newExpert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (newExpert.getStatus() != ExpertStatus.ACCEPTED) {
            throw new UserInActiveException("your account is not verified or inactive.");
        }
        if (!expert.getPassword().equals(expert.getRepeatPassword())) {
            throw new PasswordNotMatchException("the entered password does not match with its repetition.");
        }
        newExpert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expertRepository.save(newExpert);
        return new GetModifiedExpertTimeDto(LocalDateTime.now(), "the password has been successfully updated.");
    }

    @Override
    public List<Expert> findAllNewExperts() {
        return expertRepository.findAllByStatus();
    }

    @Override
    public GetModifiedExpertTimeDto confirmExpert(Long id) {
        Expert expert = expertRepository.findExpertById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("no awaiting confirmation expert found with this ID: %s.", id)));
        expert.setStatus(ExpertStatus.ACCEPTED);
        expertRepository.save(expert);
        return new GetModifiedExpertTimeDto(LocalDateTime.now(), "the expert has been successfully verified.");
    }

    @Override
    public void addExpertToSubService(Expert expert) {
        Expert newExpert = findById(expert.getId());
        boolean exist = newExpert.getSubServiceSet().stream()
                .anyMatch(t -> Objects.equals(t.getId(), expert.getSubService().getId()));
        if (exist) {
            throw new SubServiceExistException("you have already added this expert to this service.");
        }
        SubService subService = subServiceService.findById(expert.getSubService().getId());
        newExpert.addSubService(subService);
        expertRepository.save(newExpert);
    }

    @Override
    public void removeExpertFromSubService(Expert expert) {
        Expert newExpert = findById(expert.getId());
        SubService subService = newExpert.getSubServiceSet().stream()
                .filter(t -> Objects.equals(t.getId(), expert.getSubService().getId()))
                .findFirst().orElseThrow(
                        () -> new SubServiceNotFoundException("no sub services with this ID were found for this expert")
                );
        newExpert.removeSubService(subService);
        expertRepository.save(newExpert);
    }

    @Override
    public void saveExpertImage() throws IOException {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (expert.getStatus() != ExpertStatus.ACCEPTED) {
            throw new UserInActiveException("your account is not verified or inactive.");
        }
        byte[] data = expert.getImageUrl();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage image = ImageIO.read(bis);
        ImageIO.write(image, "jpg", new File("H:\\maktab83\\final_project_phase4\\final_project_phase4\\src\\main\\java\\com\\example\\images\\" + expert.getLastname() + ".jpg"));
    }

    @Override
    public void calculateExpertScore(Expert expert, LocalDateTime localDateTime) {
        long result = Duration.between(LocalDateTime.now(), localDateTime).toHours();
        Integer expertScore = expert.getScore();
        long newScore = 0;
        if (result < 0) {
            newScore = expertScore - Math.abs(result);
            if (newScore < 0) {
                expert.setStatus(ExpertStatus.INACTIVE);
            }
        }
        expert.setScore((int) newScore);
        expertRepository.save(expert);
    }

    @Override
    public void updateExpertCredit(Long money, Expert expert) {
        Long credit = expert.getCredit();
        credit += money;
        expert.setCredit(credit);
        expertRepository.save(expert);
    }

    @Override
    public void updateExpertScore(Integer score, Expert expert) {
        Integer point = expert.getScore();
        point += score;
        expert.setScore(point);
        expertRepository.save(expert);
    }

    @Override
    public Expert verifyExpert(String token) {
        Expert expert = expertRepository.findByToken(token).orElseThrow(
                () -> new UserNotFoundException("no expert found with token."));

        if (expert.getStatus() == ExpertStatus.AWAITING_CONFIRMATION) {
            throw new UserExistException(String.format("expert with this email %s is already verified by this token"
                    , expert.getEmail()));
        }
        expert.setStatus(ExpertStatus.AWAITING_CONFIRMATION);
        return expertRepository.save(expert);
    }

    @Override
    public Expert showScore() {
        return (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<Expert> search(Specification<Expert> spec) {
        return expertRepository.findAll(spec);
    }

    @Override
    public Expert showCredit() {
        return (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
