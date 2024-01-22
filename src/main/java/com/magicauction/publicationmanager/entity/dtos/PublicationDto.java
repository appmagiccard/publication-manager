package com.magicauction.publicationmanager.entity.dtos;

import com.magicauction.publicationmanager.entity.CardState;

import java.util.Date;

public record PublicationDto(
        Long publicationId,
        Long publisher_id,
        String card_name,
        String state,
        Float price,
        Date createdOn,
        Date finishedOn
) {
    public PublicationDto {
    }

    public PublicationDto(Long publicationId, Long publisherId, String cardName, String state, Float price) {
        this(publicationId, publisherId, cardName, state, price, null, null);
    }
}
