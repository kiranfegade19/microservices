package com.org.cards.cards.controllers;

import com.org.cards.cards.dto.CardDto;
import com.org.cards.cards.dto.ConfigurationEnvironmentCheckDto;
import com.org.cards.cards.dto.CustomerDto;
import com.org.cards.cards.dto.ResponseDto;
import com.org.cards.cards.entities.Card;
import com.org.cards.cards.mappers.CardMapper;
import com.org.cards.cards.services.ICardsService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(CardsController.class);

    private final ICardsService iCardsService;

    private final ConfigurationEnvironmentCheckDto configurationEnvironmentCheckDto;

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        logger.debug("Request received in CardsController : Test");
        return ResponseEntity.status(HttpStatus.OK).body("Cards test API is working fine : " + configurationEnvironmentCheckDto.getEnvApplicationConfiguration());
    }

    @PostMapping
    public ResponseEntity<ResponseDto> registerCard(@RequestBody CustomerDto customerDto) {

        logger.debug("Request received in CardsController : Register");
        Card card = iCardsService.registerCard(customerDto);

        logger.debug("Request received in CardsController : Register Done");
        if(Objects.nonNull(card)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(STATUS_NOT_FOUND, MESSAGE_NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<CardDto>> getCard(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {

        logger.debug("Request received in CardsController : GetCard");
        List<Card> cards = iCardsService.fetchCard(mobileNumber);
        List<CardDto> cardDtos = cards.stream().map(card -> CardMapper.mapToCardDto(card, new CardDto())).toList();

        logger.debug("Request received in CardsController : GetCard Done");
        return ResponseEntity.status(HttpStatus.OK).body(cardDtos);

    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardDto cardDto) {

        logger.debug("Request received in CardsController : UpdateCard");
        boolean updated = iCardsService.updateCard(cardDto);

        if(updated) {
            logger.debug("Request received in CardsController : UpdateCard Done");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_417, MESSAGE_417_UPDATE));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam Long cardId) {

        logger.debug("Request received in CardsController : DeleteCard");
        boolean deleted = iCardsService.deleteCard(cardId);

        if(deleted) {
            logger.debug("Request received in CardsController : DeleteCard Done");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_417, MESSAGE_417_DELETE));
    }

}
