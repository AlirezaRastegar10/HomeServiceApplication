package com.example.entity;


import com.example.entity.enumeration.ExpertStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Expert extends BaseUser implements UserDetails {

    @Enumerated(EnumType.STRING)
    ExpertStatus status;

    byte[] imageUrl;
    Long credit;
    Integer score;

    String role;

    String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "expert_sub_service",
            joinColumns = @JoinColumn(name = "expert_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id", referencedColumnName = "id"))
    Set<SubService> subServiceSet;

    @Transient
    SubService subService;

    @OneToMany(mappedBy = "expert")
    List<Offers> offers;

    @OneToMany(mappedBy = "expert")
    List<Comments> comments;

    @OneToMany(mappedBy = "expert")
    List<Transaction> transactions;

    public void addSubService(SubService subService) {
        subServiceSet.add(subService);
        subService.getExpertSet().add(this);
    }

    public void removeSubService(SubService subService) {
        subServiceSet.remove(subService);
        subService.getExpertSet().remove(this);
    }

    public Expert() {
        this.status = ExpertStatus.NEW;
        this.score = 0;
        this.credit = 100_000L;
        this.setRole(Expert.class.getSimpleName());
        this.subServiceSet = new HashSet<>();
        this.offers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
