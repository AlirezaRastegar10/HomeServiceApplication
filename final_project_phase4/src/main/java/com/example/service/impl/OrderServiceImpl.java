package com.example.service.impl;


import com.example.entity.*;
import com.example.entity.enumeration.ExpertStatus;
import com.example.entity.enumeration.OrderStatus;
import com.example.exceptions.order.LessProposedPriceException;
import com.example.exceptions.order.OrderNotFoundException;
import com.example.exceptions.time.LessTimeException;
import com.example.exceptions.user.LessCreditException;
import com.example.exceptions.user.UserInActiveException;
import com.example.repository.OrdersRepository;
import com.example.service.OrderService;
import com.example.utils.LocalDateTimeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final CustomerServiceImpl customerService;
    private final ExpertServiceImpl expertService;
    private final SubServiceServiceImpl subServiceService;
    private final LocalDateTimeValidation dateTimeValidation;
    private final TransactionServiceImpl transactionService;

    @Override
    public void create(Orders orders) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SubService subService = subServiceService.findById(orders.getSubService().getId());
        LocalDateTime localDateTime = dateTimeValidation.validator(orders.getTime());
        if (orders.getProposedPrice() < subService.getBasePrice()) {
            throw new LessProposedPriceException("your bid price is lower than the sub service base price.");
        }
        orders.setCustomer(customer);
        orders.setSubService(subService);
        orders.setDateAndTime(localDateTime);
        ordersRepository.save(orders);
    }

    @Override
    public List<Orders> findAllByExpertSubService() {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (expert.getStatus() != ExpertStatus.ACCEPTED) {
            throw new UserInActiveException("your account is not verified or inactive.");
        }
        return ordersRepository.findBySubService(expert.getSubServiceSet());
    }

    @Override
    public Orders findByIdAndStatus(Long id, Set<SubService> subServices) {
        return ordersRepository.findByIdAndOrderStatus(id, subServices).orElseThrow(
                () -> new OrderNotFoundException(String.format("there are no orders with this ID: %s available for your sub service.", id)));
    }

    @Override
    public void updateOrderStatusToSelect(Orders order, Offers offers) {
        List<Offers> offersList = new ArrayList<>();
        offersList.add(offers);
        order.setOffers(offersList);
        order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        ordersRepository.save(order);
    }

    @Override
    public Orders findByIdAndCustomerId(Long id, Long customerId) {
        return ordersRepository.findByIdAndCustomerId(id, customerId).orElseThrow(
                () -> new OrderNotFoundException(String.format("you do not have any orders with the status Pending Expert Selection for this order ID: %s.",  id)));
    }

    @Override
    public void updateOrderStatusToComePlace(Orders orders, Offers offers) {
        orders.setOfferSelected(offers);
        orders.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        ordersRepository.save(orders);
    }

    @Override
    public void updateOrderStatusToStarted(Orders orders) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orders newOrder = ordersRepository.findByOrderStatusAndCustomerId(orders.getId(), customer.getId())
                .orElseThrow(() -> new OrderNotFoundException(String.format("you do not have any orders with the status of waiting for an expert to come to your place with this ID: %s.", orders.getId())));
        if (LocalDateTime.now().isBefore(newOrder.getOfferSelected().getSuggestedTime())) {
            throw new LessTimeException("you cannot change the status of order to 'Started' before it starts.");
        }
        newOrder.setOrderStatus(OrderStatus.STARTED);
        ordersRepository.save(newOrder);
    }

    @Override
    public void updateOrderStatusToDone(Orders orders) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orders newOrder = ordersRepository.findStartedOrder(orders.getId(), customer.getId())
                .orElseThrow(() -> new OrderNotFoundException(String.format("you do not have any orders with the status of started with this ID: %s.", orders.getId())));
        newOrder.setOrderStatus(OrderStatus.DONE);
        expertService.calculateExpertScore(newOrder.getOfferSelected().getExpert(), newOrder.getOfferSelected().getDurationOfWork());
        ordersRepository.save(newOrder);
    }

    @Override
    public List<Orders> findAllDoneOrder() {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ordersRepository.findAllDoneOrder(customer.getId());
    }

    @Override
    public Orders checkDoneOrder(Orders orders, Customer customer) {
        return ordersRepository.findOrderForPayment(orders.getId(), customer.getId())
                .orElseThrow(() -> new OrderNotFoundException(String.format("you do not have any orders with the status of done with this ID: %s", orders.getId())));
    }

    @Override
    public void paymentWithOneMethod(Orders orders) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orders newOrder = checkDoneOrder(orders, customer);
        if (customer.getCredit() < newOrder.getOfferSelected().getProposedPrice()) {
            throw new LessCreditException("your inventory is insufficient.");
        }
        Long money = (newOrder.getOfferSelected().getProposedPrice() * 70) / 100;
        newOrder.setOrderStatus(OrderStatus.PAID);
        Orders saved = ordersRepository.save(newOrder);
        expertService.updateExpertCredit(money, newOrder.getOfferSelected().getExpert());
        customerService.updateCustomerCredit(newOrder.getOfferSelected().getProposedPrice(), customer);

        Transaction transaction = new Transaction(
                customer, saved.getOfferSelected().getExpert(), saved.getSubService(), saved.getSubService().getServices(),
                saved.getOfferSelected().getProposedPrice(), "By Credit", saved.getOrderStatus().name());
        transactionService.create(transaction);
    }

    @Override
    public Boolean paymentWithTwoMethod(Orders orders) {
        Orders newOrder = ordersRepository.findById(orders.getId()).orElseThrow(
                () -> new OrderNotFoundException(String.format("you do not have any orders with the status of done with this ID: %s", orders.getId())));
        Long money = (newOrder.getOfferSelected().getProposedPrice() * 70) / 100;
        newOrder.setOrderStatus(OrderStatus.PAID);
        Orders saved = ordersRepository.save(newOrder);
        expertService.updateExpertCredit(money, newOrder.getOfferSelected().getExpert());

        Transaction transaction = new Transaction(
                saved.getCustomer(), saved.getOfferSelected().getExpert(), saved.getSubService(), saved.getSubService().getServices(),
                saved.getOfferSelected().getProposedPrice(), "By Credit Card", saved.getOrderStatus().name());
        transactionService.create(transaction);

        return true;
    }

    @Override
    public Orders findPaidOrder(Long id, Long customerId) {
        return ordersRepository.findPaidOrder(id, customerId).orElseThrow(
                () -> new OrderNotFoundException(String.format("you do not have any orders with the status of paid with this ID: %s", id)));
    }

    @Override
    public List<Orders> findAllOrder(OrderStatus status) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ordersRepository.findAllByStatus(customer.getId(), status);
    }

    @Override
    public List<Orders> findAllExpertOrder(OrderStatus status) {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ordersRepository.findAllByExpertId(expert.getId(), status);
    }
}
