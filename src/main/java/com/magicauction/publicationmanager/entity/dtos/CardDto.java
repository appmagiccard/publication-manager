package com.magicauction.publicationmanager.entity.dtos;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public record CardDto(
        String name,
        String scryfallId,
        String imgStatus,
        boolean isFoil,
        String setName,
        Date lastMod,
        HashMap<String, Float> prices,
        HashMap<String, String> imageUri,
        HashMap<String, String> relatedUri,
        HashMap<String, String> purchaseUri
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDto cardPojo = (CardDto) o;
        return isFoil == cardPojo.isFoil && Objects.equals(name, cardPojo.name) && scryfallId.equals(cardPojo.scryfallId) && Objects.equals(imgStatus, cardPojo.imgStatus) && Objects.equals(setName, cardPojo.setName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scryfallId, imgStatus, isFoil, setName);
    }
}
