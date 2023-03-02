package com.example.service.impl;


import com.example.entity.Comments;
import com.example.entity.Customer;
import com.example.entity.Orders;
import com.example.repository.CommentRepository;
import com.example.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;

    @Override
    public Comments create(Comments comments) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orders orders = orderService.findPaidOrder(comments.getOrders().getId(), customer.getId());
        comments.setExpert(orders.getOfferSelected().getExpert());
        comments.setCustomer(customer);
        expertService.updateExpertScore(comments.getScore(), orders.getOfferSelected().getExpert());
        return commentRepository.save(comments);
    }
}
