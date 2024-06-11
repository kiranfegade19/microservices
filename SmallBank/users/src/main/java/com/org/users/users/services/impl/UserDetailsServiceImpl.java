package com.org.users.users.services.impl;

import com.org.users.users.dto.CardDto;
import com.org.users.users.dto.CustomerDto;
import com.org.users.users.dto.UserDetailsDto;
import com.org.users.users.entity.Customer;
import com.org.users.users.exceptions.ResourceNotFoundException;
import com.org.users.users.mapper.CustomerMapper;
import com.org.users.users.repositories.UserRepository;
import com.org.users.users.services.IUserDetailsService;
import com.org.users.users.services.clients.CardsFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements IUserDetailsService {


    private final UserRepository userRepository;
    private final CardsFeignClient cardsFeignClient;

    @Override
    public UserDetailsDto fetchUserDetails(String mobileNumber) {

        Customer customer = userRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("User", "mobileNumber", mobileNumber)
        );

        ResponseEntity<List<CardDto>> cardDtosResponseEntity = cardsFeignClient.getCard(mobileNumber);

        CardDto cardDto = null;
        if(cardDtosResponseEntity.getStatusCode() != HttpStatus.EXPECTATION_FAILED) {
            cardDto = cardDtosResponseEntity.getBody().get(0);
        }

        UserDetailsDto userDetailsDto = new UserDetailsDto(CustomerMapper.mapToCustomerDto(customer, new CustomerDto()), cardDto);

        return userDetailsDto;
    }

}
