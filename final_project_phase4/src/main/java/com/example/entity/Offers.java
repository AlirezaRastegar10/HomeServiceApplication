package com.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Offers extends BaseEntity<Long> {

    @ManyToOne
    Expert expert;

    @Transient
    String email;

    @CreationTimestamp
    LocalDateTime registerDateAndTime;

    Long proposedPrice;

    @Transient
    String suggestedDate;

    LocalDateTime suggestedTime;

    @Transient
    String workTime;

    LocalDateTime durationOfWork;

    @ManyToOne
    Orders order;
}
