package com.org.cards.cards.controllers;

import com.org.cards.cards.dto.CardDto;
import com.org.cards.cards.dto.CustomerDto;
import com.org.cards.cards.dto.ResponseDto;
import com.org.cards.cards.entities.Card;
import com.org.cards.cards.services.ICardsService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.org.cards.cards.constants.CardConstants.*;

@RestController
@RequestMapping(path = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CardsController {

    private final ICardsService iCardsService;

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.status(HttpStatus.OK).body("Cards test API is working fine");
    }

    @PostMapping
    public ResponseEntity<ResponseDto> registerCard(@RequestBody CustomerDto customerDto) {

        Card card = iCardsService.registerCard(customerDto);

        if(Objects.nonNull(card)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(STATUS_NOT_FOUND, MESSAGE_NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Card>> getCard(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {

        List<Card> cards = iCardsService.fetchCards(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(cards);

    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardDto cardDto) {

        boolean updated = iCardsService.updateCard(cardDto);

        if(updated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_417, MESSAGE_417_UPDATE));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam Long cardId) {

        boolean deleted = iCardsService.deleteCard(cardId);

        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_417, MESSAGE_417_DELETE));
    }

}
