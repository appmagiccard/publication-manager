package com.magicauction.publicationmanager.processor;

import com.magicauction.publicationmanager.entity.dtos.PublicationDto;
import com.magicauction.publicationmanager.entity.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IPublicationProcessor {
    Optional<PublicationDto> findPubById(Long pubId);
    List<PublicationDto> findAllPubs();
    Optional<PublicationDto> createNewPub(PublicationDto inputPub) throws UserNotFoundException;
    Optional<PublicationDto> updatePub(Long pubId, PublicationDto inputPub) throws UserNotFoundException;
    Boolean deletePubById(Long pubId);
    List<PublicationDto> finishPubsByIds(List<Long> pubIds);
}
