package com.example.repository;

import com.example.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {

    Optional<Expert> findByEmail(String email);

    @Query("select e from Expert e where e.status='AWAITING_CONFIRMATION'")
    List<Expert> findAllByStatus();

    @Query("select e from Expert e where e.id= ?1 and e.status='AWAITING_CONFIRMATION'")
    Optional<Expert> findExpertById(Long id);

    Optional<Expert> findByToken(String token);
}
