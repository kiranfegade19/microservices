package com.org.cards.cards.dto;

import com.org.cards.cards.enums.CardType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDto {


    private String mobileNumber;

    private String cardNumber;

    private CardType cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;

}
