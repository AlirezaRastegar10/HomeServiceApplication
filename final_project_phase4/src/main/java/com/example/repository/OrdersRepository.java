package com.example.repository;

import com.example.entity.Orders;
import com.example.entity.SubService;
import com.example.entity.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o where o.subService in ?1 and " +
            "(o.orderStatus='WAITING_FOR_THE_SUGGESTION_OF_EXPERTS' or o.orderStatus='WAITING_FOR_SPECIALIST_SELECTION')")
    List<Orders> findBySubService(Set<SubService> subServices);

    @Query("select o from Orders o where o.id= ?1 and o.subService in ?2 and " +
            "(o.orderStatus='WAITING_FOR_THE_SUGGESTION_OF_EXPERTS' or o.orderStatus='WAITING_FOR_SPECIALIST_SELECTION')")
    Optional<Orders> findByIdAndOrderStatus(Long id, Set<SubService> subServices);

    @Query("select o from Orders o where o.id= ?1 and o.customer.id= ?2 and o.orderStatus='WAITING_FOR_SPECIALIST_SELECTION'")
    Optional<Orders> findByIdAndCustomerId(Long id, Long customerId);

    @Query("select o from Orders o where o.id= ?1 and o.customer.id= ?2 and o.orderStatus='WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE'")
    Optional<Orders> findByOrderStatusAndCustomerId(Long id, Long customerId);

    @Query("select o from Orders o where o.id= ?1 and o.customer.id= ?2 and o.orderStatus='STARTED'")
    Optional<Orders> findStartedOrder(Long id, Long customerId);

    @Query("select o from Orders o where o.customer.id= ?1 and o.orderStatus='DONE'")
    List<Orders> findAllDoneOrder(Long customerId);

    @Query("select o from Orders o where o.id= ?1 and o.customer.id= ?2 and o.orderStatus='DONE'")
    Optional<Orders> findOrderForPayment(Long id, Long customerId);

    @Query("select o from Orders o where o.id= ?1 and o.customer.id= ?2 and o.orderStatus='PAID'")
    Optional<Orders> findPaidOrder(Long id, Long customerId);

    @Query("select o from Orders o where o.customer.id= ?1 and o.orderStatus= ?2")
    List<Orders> findAllByStatus(Long customerId, OrderStatus orderStatus);

    @Query("select o from Orders o where o.offerSelected.expert.id= ?1 and o.orderStatus= ?2")
    List<Orders> findAllByExpertId(Long expertId, OrderStatus orderStatus);
}
