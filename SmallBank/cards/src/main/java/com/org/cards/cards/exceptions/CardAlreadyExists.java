package com.org.cards.cards.exceptions;

public class CardAlreadyExists extends RuntimeException {

    public CardAlreadyExists(String message) {
        super(message);
    }
}
