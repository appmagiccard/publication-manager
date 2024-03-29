package com.magicauction.publicationmanager.processor;

import com.magicauction.publicationmanager.entity.Card;
import com.magicauction.publicationmanager.entity.CardState;
import com.magicauction.publicationmanager.entity.Publication;
import com.magicauction.publicationmanager.entity.User;
import com.magicauction.publicationmanager.entity.dtos.PublicationDto;
import com.magicauction.publicationmanager.entity.exceptions.CardNotFoundException;
import com.magicauction.publicationmanager.entity.exceptions.UserNotFoundException;
import com.magicauction.publicationmanager.entity.repository.PublicationRepository;
import com.magicauction.publicationmanager.entity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationProcessor implements IPublicationProcessor{

    private final PublicationRepository repository;
    private static final Logger log = LoggerFactory.getLogger(PublicationProcessor.class);
    private final UserRepository userRepository;
    private final ICardFinder cardFinder;

    @Autowired
    public PublicationProcessor(PublicationRepository repository, UserRepository userRepository, ICardFinder cardFinder) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.cardFinder = cardFinder;
    }

    @Override
    public Optional<PublicationDto> findPubById(Long pubId) {
        return repository.findById(pubId).map(this::toDto);
    }


    @Override
    public List<PublicationDto> findAllPubs() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PublicationDto> createNewPub(PublicationDto inputPub) throws UserNotFoundException, CardNotFoundException {
        //Valid user?
        //valid Card?
        if(validateInputPub(inputPub)){
            log.info("inputPub not valid: {}", inputPub);
            return Optional.empty();
        }
        log.info("Creating new pub with input: [{}]", inputPub);
        return Optional.of(toDto(repository.save(toEntity(inputPub))));
    }


    @Override
    public Optional<PublicationDto> updatePub(Long pubId, PublicationDto inputPub) throws UserNotFoundException {
        if(validateInputPub(inputPub)){
            log.info("inputPub not valid: {}", inputPub);
            return Optional.empty();
        }
        Optional<Publication> optionalPublication = repository.findById(pubId);
        if (optionalPublication.isEmpty()){
            log.info("inputPub not found: {}", inputPub);
            return Optional.empty();
        }
        log.info("Updating pub {} with input: [{}]", pubId, inputPub);
        Publication publication = optionalPublication.get();
        publication.setPrice(inputPub.price());
        publication.setFinishedOn(inputPub.finishedOn() != null ? inputPub.finishedOn() : null);
        publication.setCardState(CardState.valueOfLabel(inputPub.state()));
        repository.save(publication);
        return Optional.of(toDto(publication));
    }

    @Override
    public Boolean deletePubById(Long pubId) {
        Optional<Publication> optPub = repository.findById(pubId);
        if (optPub.isPresent()) {
            repository.delete(optPub.get());
            return true;
        }
        return false;
    }

    @Override
    public List<PublicationDto> finishPubsByIds(List<Long> pubIds) {
        return repository.findAllById(pubIds)
                .stream()
                .filter(p -> p.getFinishedOn() == null)
                .map(this::updateToFinish)
                .map(repository::save)
                .map(this::toDto)
                .collect(Collectors.toList())
                ;
    }

    private Publication updateToFinish(Publication p) {
        p.setFinishedOn(new Date());
        return p;
    }


    private PublicationDto toDto(Publication publication) {
        return new PublicationDto(
                publication.getPublicationId(),
                publication.getPublisher().getUserId(),
                publication.getCard().getName(),
                publication.getCardState().getLabel(),
                publication.getPrice(),
                publication.getCreatedOn(),
                publication.getFinishedOn()
        );
    }

    private Publication toEntity(PublicationDto inputPub) throws UserNotFoundException, CardNotFoundException {
        User publisher = findUserById(inputPub.publisher_id());
        Card card = cardFinder.findByName(inputPub.card_name());
        return toEntity(inputPub, card, publisher);
    }

    private Publication toEntity(PublicationDto inputPub, Card card, User publisher) throws UserNotFoundException {
        return new Publication(card, publisher, inputPub.price(), inputPub.state());
    }

    private User findUserById(Long id) throws UserNotFoundException {
        //Puede cambiar a una llamada REST cuando exista el servicio
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private boolean validateInputPub(PublicationDto inputPub) {
        //TODO: finish this!!
        return false;
    }
}
