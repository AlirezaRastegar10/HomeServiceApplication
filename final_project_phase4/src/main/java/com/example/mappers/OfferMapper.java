package com.example.mappers;


import com.example.dto.offer.CreateOfferDto;
import com.example.dto.offer.GetOfferForCustomerDto;
import com.example.dto.offer.SelectOfferDto;
import com.example.dto.order.GetOrderByCustomerEmailDto;
import com.example.entity.Offers;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {
    Offers offerDtoToOffer(CreateOfferDto createOfferDto);
    List<GetOfferForCustomerDto> offerListToDtoList(List<Offers> offersList);
    Offers offerCustomerDtoToOffer(GetOrderByCustomerEmailDto getOrderByCustomerEmailDto);
    Offers selectOfferDtoToOffer(SelectOfferDto selectOfferDto);
}
