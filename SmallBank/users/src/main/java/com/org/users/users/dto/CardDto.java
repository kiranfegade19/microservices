package com.org.users.users.dto;

import com.org.users.users.enums.CardType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDto {


    private String mobileNumber;

    private String cardNumber;

    private CardType cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;

}
