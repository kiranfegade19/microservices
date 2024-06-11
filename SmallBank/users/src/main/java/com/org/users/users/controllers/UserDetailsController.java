package com.org.users.users.controllers;

import com.org.users.users.dto.UserDetailsDto;
import com.org.users.users.services.IUserDetailsService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/userdetails", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserDetailsController {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);

    private final IUserDetailsService iUserDetailsService;


    @GetMapping
    public ResponseEntity<UserDetailsDto> getUserDetails(@RequestParam
                                                             @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                             String mobileNumber) {

        logger.debug("Request received in UserDetailsController : Fetch");
        UserDetailsDto userDetailsDto = iUserDetailsService.fetchUserDetails(mobileNumber);

        logger.debug("Request received in UserDetailsController : Fetch Done");
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsDto);
    }
}
