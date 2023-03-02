package com.example.repository;

import com.example.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    @Query("select s from SubService s where s.services.id= ?1")
    List<SubService> findAllByServices(Long id);
}
