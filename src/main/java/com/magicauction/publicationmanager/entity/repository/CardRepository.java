package com.magicauction.publicationmanager.entity.repository;

import com.magicauction.publicationmanager.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByScryfallId(String scryfallId);
    List<Card> findAllByScryfallIdIn(Iterable<String> scryfallId);
    List<Card> findAllByName(String name);
}
