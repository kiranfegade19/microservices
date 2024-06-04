package com.org.cards.cards.services;

import com.org.cards.cards.dto.CardDto;
import com.org.cards.cards.dto.CustomerDto;
import com.org.cards.cards.dto.ResponseDto;
import com.org.cards.cards.entities.Card;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICardsService {

    public Card registerCard(CustomerDto customerDto);

    public boolean updateCard(CardDto cardDto);

    public List<Card> fetchCards(String mobileNumber);

    public Optional<Card> fetchCard(Long cardId);

    public boolean deleteCards(CustomerDto customerDto);

    public boolean deleteCard(Long cardId);
}
