package com.magicauction.publicationmanager.entity.exceptions;

public class CardNotFoundException extends PublicationManagerException{
    private final static String BASE_MSG = "No Card Found with %s %s";
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(String.format(BASE_MSG, "name: ", message));
    }
}
