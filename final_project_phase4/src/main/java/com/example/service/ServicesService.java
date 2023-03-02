package com.example.service;


import com.example.entity.Services;

import java.util.List;

public interface ServicesService {

    void save(Services services);
    List<Services> findAll();
    Services findById(Long id);
}
