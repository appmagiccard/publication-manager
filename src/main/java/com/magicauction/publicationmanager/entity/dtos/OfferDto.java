package com.magicauction.publicationmanager.entity.dtos;

import com.magicauction.publicationmanager.entity.TradeStatus;

import java.util.Date;
import java.util.List;

public record OfferDto(
    Long offerId,
    Long publisherId,
    Long buyerId,
    List<Long> publications,
    Date created,
    Date finished,
    TradeStatus status
    ){

    public OfferDto(Long publisherId, Long buyerId, List<Long> publications) {
        this(null, publisherId, buyerId, publications, null, null, null);
    }
}
