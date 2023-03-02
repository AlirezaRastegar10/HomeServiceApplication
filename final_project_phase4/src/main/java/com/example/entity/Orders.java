package com.example.entity;

import com.example.entity.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Orders extends BaseEntity<Long> {

    @ManyToOne
    Customer customer;

    @Transient
    String email;

    Long proposedPrice;
    String description;
    LocalDateTime dateAndTime;

    @Transient
    String time;

    String address;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @OneToOne()
    SubService subService;

    @OneToOne()
    Offers offerSelected;

    @OneToMany(mappedBy = "order")
    List<Offers> offers;

    public Orders() {
        this.orderStatus = OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS;
        this.offers = new ArrayList<>();
    }
}
