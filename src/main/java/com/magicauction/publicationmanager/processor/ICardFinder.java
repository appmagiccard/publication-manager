package com.magicauction.publicationmanager.processor;

import com.magicauction.publicationmanager.entity.Card;
import com.magicauction.publicationmanager.entity.exceptions.CardNotFoundException;

public interface ICardFinder {
    Card findByName(String cardName) throws CardNotFoundException;
}
