package com.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Services extends BaseEntity<Long> {

    @Column(unique = true)
    String name;

    @OneToMany(mappedBy = "services")
    List<SubService> subServices;

    public Services() {
        this.subServices = new ArrayList<>();
    }
}
