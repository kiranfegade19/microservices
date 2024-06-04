package com.org.cards.cards.mappers;

import com.org.cards.cards.dto.CardDto;
import com.org.cards.cards.entities.Card;

public class CardMapper {

    public static CardDto mapToCardDto(Card card, CardDto cardDto) {

        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setTotalLimit(card.getTotalLimit());

        return cardDto;
    }

    public static Card mapToCard(CardDto cardDto, Card card) {

        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setTotalLimit(cardDto.getTotalLimit());
        return card;
    }

}
