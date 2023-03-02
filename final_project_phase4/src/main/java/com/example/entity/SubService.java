package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNameAndServiceId", columnNames = {"name","services_id"})
})
public class SubService extends BaseEntity<Long> {


    String name;
    Long basePrice;
    String explanation;

    @ManyToMany(mappedBy = "subServiceSet", fetch = FetchType.EAGER)
    Set<Expert> expertSet;

    @ManyToOne
    Services services;

    public SubService() {
        this.expertSet = new HashSet<>();
    }
}
