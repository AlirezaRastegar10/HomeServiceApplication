package com.example.controller;


import com.example.dto.expert.*;
import com.example.dto.search.APIResponse;
import com.example.entity.Expert;
import com.example.mappers.ExpertMapperImpl;
import com.example.search.ExpertSpecificationBuilder;
import com.example.search.SearchCriteria;
import com.example.search.SearchDto;
import com.example.service.impl.ExpertServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/expert")
public class ExpertController {

    private final ExpertServiceImpl expertService;
    private final ExpertMapperImpl expertMapper;

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('Admin')")
    public GetExpertDto findById(@PathVariable Long id) {
        return expertMapper.expertToDto(expertService.findById(id));
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasRole('Expert')")
    public GetModifiedExpertTimeDto changePassword(@Valid @RequestBody ChangeExpertPasswordDto changeExpertPasswordDto) {
        Expert expert = expertMapper.changePasswordDtoToExpert(changeExpertPasswordDto);
        return expertService.changePassword(expert);
    }

    @GetMapping("/findAllNewExpert")
    @PreAuthorize("hasRole('Admin')")
    public List<GetExpertDto> findAllNewExpert() {
        return expertMapper.expertListToDtoList(expertService.findAllNewExperts());
    }

    @PutMapping("/confirmExpert/{id}")
    @PreAuthorize("hasRole('Admin')")
    public GetModifiedExpertTimeDto confirmExpert(@PathVariable Long id) {
        return expertService.confirmExpert(id);
    }

    @PutMapping("/addExpertToSubService")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> addExpertToSubService(@Valid @RequestBody ExpertToSubServiceDto expertToSubServiceDto) {
        Expert expert = expertMapper.expertSubServiceToExpert(expertToSubServiceDto);
        expertService.addExpertToSubService(expert);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/removeExpertFromSubService")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> removeExpertFromSubService(@Valid @RequestBody ExpertToSubServiceDto expertToSubServiceDto) {
        Expert expert = expertMapper.expertSubServiceToExpert(expertToSubServiceDto);
        expertService.removeExpertFromSubService(expert);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveExpertImage")
    @PreAuthorize("hasRole('Expert')")
    public ResponseEntity<String> saveExpertImage() throws IOException {
        expertService.saveExpertImage();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/showMyScore")
    @PreAuthorize("hasRole('Expert')")
    public GetScoreDto showScore() {
        return expertMapper.expertToScoreDto(expertService.showScore());
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<APIResponse> findAll(@RequestBody SearchDto searchDto) {
        APIResponse apiResponse = new APIResponse();
        ExpertSpecificationBuilder builder = new ExpertSpecificationBuilder();
        List<SearchCriteria> criteriaList = searchDto.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(searchDto
                        .getDataOption());
                builder.with(x);
            });
        }

        List<Expert> expertList = expertService.search(builder.build());
        apiResponse.setData(expertMapper.expertListToDtoList(expertList));
        apiResponse.setResponseCode(HttpStatus.OK);
        apiResponse.setMessage("Successfully retrieved expert record");

        return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
    }

    @GetMapping("/showCredit")
    @PreAuthorize("hasRole('Expert')")
    public GetExpertCreditDto showCredit() {
        return expertMapper.expertToCreditDto(expertService.showCredit());
    }
}
