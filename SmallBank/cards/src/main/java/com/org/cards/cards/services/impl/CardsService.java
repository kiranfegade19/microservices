package com.org.cards.cards.services.impl;

import com.org.cards.cards.dto.CardDto;
import com.org.cards.cards.dto.CustomerDto;
import com.org.cards.cards.entities.Card;
import com.org.cards.cards.enums.CardType;
import com.org.cards.cards.exceptions.CardAlreadyExists;
import com.org.cards.cards.exceptions.ResourceNotFoundException;
import com.org.cards.cards.repositories.CardsRepository;
import com.org.cards.cards.services.ICardsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.org.cards.cards.mappers.CardMapper.mapToCard;

@Service
@AllArgsConstructor
public class CardsService implements ICardsService {

    private final CardsRepository cardsRepository;

    @Override
    public Card registerCard(CustomerDto customerDto) {

        Optional<Card> card = cardsRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (card.isPresent()) {
            throw new CardAlreadyExists(String.format("Card is already present for user with mobileNumber : %s", customerDto.getMobileNumber()) );
        }

        card = Optional.of(Card.builder()
                .cardNumber("1234 1234 1234 1234")
                .cardType(CardType.DEBIT)
                .mobileNumber(customerDto.getMobileNumber())
                .build());

        Card savedCard = cardsRepository.save(card.get());

        return savedCard;
    }

    @Override
    public boolean updateCard(CardDto cardDto) {

        Card savedCard = cardsRepository.findByMobileNumber(cardDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Card with MobileNumber : %s, not found", cardDto.getMobileNumber()))
        );

        savedCard = mapToCard(cardDto, savedCard);

        cardsRepository.save(savedCard);

        return true;
    }

//    @Override
//    public List<Card> fetchCards(String mobileNumber) {
//
//        Optional<Card> card = cardsRepository.findByMobileNumber(mobileNumber);
//
//        return card.map(List::of).orElseGet(List::of);
//
//    }

    @Override
    public List<Card> fetchCard(String mobileNumber) {

        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException( String.format("Card with mobile number {} not found", mobileNumber).toString() )
        );

        return List.of(card);

    }

    @Transactional
    @Override
    public boolean deleteCards(CustomerDto customerDto) {

        Card card = cardsRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Card with MobileNumber : %s, not found", customerDto.getMobileNumber()))
        );

        cardsRepository.delete(card);
        return true;
    }

    @Override
    public boolean deleteCard(Long cardId) {

        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Card with CardId : %s, not found", cardId))
        );

        cardsRepository.delete(card);
        return true;
    }
}
