package com.example.repository;

import com.example.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {

    @Query("select o from Offers o where o.order.id= ?1 order by o.proposedPrice desc ")
    List<Offers> findByOrderIdOrderByProposedPrice(Long orderId);

    @Query("select o from Offers o where o.order.id= ?1 order by o.expert.score desc ")
    List<Offers> findByOrderIdOrderByExpertScore(Long orderId);

    @Query("select o from Offers o where o.id= ?1 and o.order.id= ?2")
    Optional<Offers> findByIdAndOrderId(Long id, Long orderId);
}
