package com.org.users.users.services.clients;

import com.org.users.users.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cards", fallback = CardsFeignClientFallback.class)
public interface CardsFeignClient {

    @GetMapping(value= "/api/v1/cards")
    public ResponseEntity<List<CardDto>> getCard(@RequestParam String mobileNumber);
}
