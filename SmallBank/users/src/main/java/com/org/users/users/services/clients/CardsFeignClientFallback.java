package com.org.users.users.services.clients;

import com.org.users.users.dto.CardDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardsFeignClientFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<List<CardDto>> getCard(String mobileNumber) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body( List.of(new CardDto()) );
    }
}
