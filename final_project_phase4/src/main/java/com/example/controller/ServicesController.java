package com.example.controller;


import com.example.dto.service.GetServicesDto;
import com.example.dto.service.ServicesDto;
import com.example.entity.Services;
import com.example.mappers.ServicesMapperImpl;
import com.example.service.impl.ServicesServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/services")
public class ServicesController {

    private final ServicesServiceImpl service;
    private final ServicesMapperImpl servicesMapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> create(@Valid @RequestBody ServicesDto servicesDto) {
        Services services = servicesMapper.dtoToServices(servicesDto);
        service.save(services);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('Admin') or hasRole('Customer')")
    public List<GetServicesDto> findAll() {
        return servicesMapper.servicesListToDtoList(service.findAll());
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('Admin') or hasRole('Customer')")
    public GetServicesDto findById(@PathVariable Long id) {
        return servicesMapper.servicesToDto(service.findById(id));
    }
}
