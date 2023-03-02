package com.example.service;


import com.example.entity.Offers;

import java.util.List;

public interface OfferService {

    void create(Offers offers);
    List<Offers> orderByProposedPrice(Offers offers);
    List<Offers> orderByScore(Offers offers);
    void selectOffer(Offers offers);
}
