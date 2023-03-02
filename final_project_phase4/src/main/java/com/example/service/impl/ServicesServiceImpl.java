package com.example.service.impl;


import com.example.entity.Services;
import com.example.exceptions.service.ServiceExistException;
import com.example.exceptions.service.ServiceNotFoundException;
import com.example.repository.ServiceRepository;
import com.example.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {

    private final ServiceRepository serviceRepository;

    @Override
    public void save(Services services) {
        try {
            serviceRepository.save(services);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceExistException(String.format("a service already exists with this name: %s.", services.getName()));
        }
    }

    @Override
    public List<Services> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Services findById(Long id) {
        return serviceRepository.findById(id).orElseThrow(
                () -> new ServiceNotFoundException(String.format("no service found with this ID: %s.", id)));
    }
}
