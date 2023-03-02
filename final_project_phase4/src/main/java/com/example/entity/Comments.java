package com.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Comments extends BaseEntity<Long> {

    @ManyToOne
    Customer customer;

    @ManyToOne
    Expert expert;

    @Transient
    Orders orders;

    @Transient
    String email;

    Integer score;
    String content;
}
