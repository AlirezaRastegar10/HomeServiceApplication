package com.example.controller;


import com.example.dto.subservice.GetSubServiceDto;
import com.example.dto.subservice.SubServiceDto;
import com.example.dto.subservice.UpdateSubServiceDto;
import com.example.entity.SubService;
import com.example.mappers.SubServiceMapperImpl;
import com.example.service.impl.SubServiceServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/subService")
public class SubServiceController {

    private final SubServiceServiceImpl service;
    private final SubServiceMapperImpl subServiceMapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> create(@Valid @RequestBody SubServiceDto subServiceDto) {
        SubService subService = subServiceMapper.dtoToSubService(subServiceDto);
        service.create(subService);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('Admin') or hasRole('Customer')")
    public GetSubServiceDto findById(@PathVariable Long id) {
        return subServiceMapper.subServiceToDto(service.findById(id));
    }

    @GetMapping("/findByService/{id}")
    @PreAuthorize("hasRole('Admin')")
    public List<GetSubServiceDto> findAllByServiceId(@PathVariable Long id) {
        return subServiceMapper.subServiceListToDtoList(service.findAllByServiceId(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('Admin')")
    public GetSubServiceDto update(@Valid @RequestBody UpdateSubServiceDto updateSubServiceDto) {
        SubService founded = service.findById(updateSubServiceDto.getId());
        SubService subService = subServiceMapper.updateSubServiceDtoToDto(updateSubServiceDto, founded);
        return subServiceMapper.subServiceToDto(service.update(subService));
    }
}
