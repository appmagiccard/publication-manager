package com.magicauction.publicationmanager.controller;

import com.magicauction.publicationmanager.entity.dtos.PublicationDto;
import com.magicauction.publicationmanager.entity.exceptions.UserNotFoundException;
import com.magicauction.publicationmanager.processor.IPublicationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("publication")
public class PublicationController {
    private static final Logger log = LoggerFactory.getLogger(PublicationController.class);
    private final IPublicationProcessor publicationProcessor;

    @Autowired
    public PublicationController(IPublicationProcessor publicationProcessor) {
        this.publicationProcessor = publicationProcessor;
    }


    @GetMapping("/{pubId}")
    public ResponseEntity<PublicationDto> getPublicationByPubId(@PathVariable Long pubId){
        return publicationProcessor.findPubById(pubId)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse)
                ;
    }


    @GetMapping
    public ResponseEntity<List<PublicationDto>> getAllPublications(){
        return ResponseEntity.ok(publicationProcessor.findAllPubs());
    }


    @PostMapping
    public ResponseEntity<PublicationDto> createPublication(@RequestBody PublicationDto inputPub) throws UserNotFoundException {
        return publicationProcessor.createNewPub(inputPub)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse)
                ;
    }


    @PutMapping("/{pubId}")
    public ResponseEntity<PublicationDto> updatePublicationById(@PathVariable Long pubId, @RequestBody PublicationDto inputPub) throws UserNotFoundException {
        return publicationProcessor.updatePub(pubId, inputPub)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse)
                ;
    }


    @DeleteMapping("/{pubId}")
    public ResponseEntity<Boolean> deletePublicationById(@PathVariable Long pubId){
        return ResponseEntity.ok(publicationProcessor.deletePubById(pubId));
    }

    @PutMapping("/finish")
    public ResponseEntity<List<PublicationDto>> updateToFinishByIds(@RequestBody List<Long> ids){
        return ResponseEntity.ok(publicationProcessor.finishPubsByIds(ids));
    }

    private ResponseEntity<PublicationDto> notFoundResponse() {
        return ResponseEntity.status(404).build();
    }

}
