package com.example.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class BaseUser extends BaseEntity<Long> {

    String firstname;

    String lastname;

    @Column(unique = true)
    String email;

    String password;

    @Transient
    String repeatPassword;

    @CreationTimestamp
    LocalDateTime registerDate;

    @Transient
    LocalDateTime modifiedTime;

    @Transient
    String modifiedText;
}
