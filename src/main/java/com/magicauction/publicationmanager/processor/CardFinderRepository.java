package com.magicauction.publicationmanager.processor;

import com.magicauction.publicationmanager.entity.Card;
import com.magicauction.publicationmanager.entity.exceptions.CardNotFoundException;
import com.magicauction.publicationmanager.entity.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardFinderRepository implements ICardFinder{
    private final CardRepository repository;

    @Autowired
    public CardFinderRepository(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card findByName(String cardName) throws CardNotFoundException {
        return repository.findAllByName(cardName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(cardName));
    }
}
