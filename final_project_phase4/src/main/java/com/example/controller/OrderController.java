package com.example.controller;


import com.example.dto.order.*;
import com.example.entity.Orders;
import com.example.entity.enumeration.OrderStatus;
import com.example.mappers.OrderMapperImpl;
import com.example.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final OrderMapperImpl orderMapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<String> create(@Valid @RequestBody CreateOrderDto createOrderDto) {
        Orders orders = orderMapper.orderDtoToOrder(createOrderDto);
        orderService.create(orders);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/findAllByExpertSubService")
    @PreAuthorize("hasRole('Expert')")
    public List<GetOrderForExpertDto> findAllByExpertSubService() {
        return orderMapper.orderListToDtoList(orderService.findAllByExpertSubService());
    }

    @PostMapping("/startOrder")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<String> changeOrderStatusToStarted(@Valid @RequestBody GetOrderByCustomerEmailDto getOrderByCustomerEmailDto) {
        Orders orders = orderMapper.orderCustomerEmailDtoToOrder(getOrderByCustomerEmailDto);
        orderService.updateOrderStatusToStarted(orders);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/doneOrder")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<String> changeOrderStatusToDone(@Valid @RequestBody GetOrderByCustomerEmailDto getOrderByCustomerEmailDto) {
        Orders orders = orderMapper.orderCustomerEmailDtoToOrder(getOrderByCustomerEmailDto);
        orderService.updateOrderStatusToDone(orders);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/findAllDoneOrder")
    @PreAuthorize("hasRole('Customer')")
    public List<GetOrderWithOfferDto> findAllDoneOrder() {
        return orderMapper.orderListToOrderListDto(orderService.findAllDoneOrder());
    }

    @PostMapping("/customer/showMyOrders")
    @PreAuthorize("hasRole('Customer')")
    public List<GetOrderWithOfferDto> showMyOrders(@RequestParam(value = "status", required = false) String status) {
        return orderMapper.orderListToOrderListDto(orderService.findAllOrder(OrderStatus.valueOf(status.toUpperCase())));
    }

    @PostMapping("/expert/showMyOrders")
    @PreAuthorize("hasRole('Expert')")
    public List<GetOrderWithOfferDto> showExpertOrders(@RequestParam(value = "status", required = false) String status) {
        return orderMapper.orderListToOrderListDto(orderService.findAllExpertOrder(OrderStatus.valueOf(status.toUpperCase())));
    }
}
