package com.org.users.users.services.impl;

import com.org.users.users.dto.CustomerDto;
import com.org.users.users.dto.UserMsgDto;
import com.org.users.users.entity.Customer;
import com.org.users.users.exceptions.CustomerAlreadyExistsException;
import com.org.users.users.exceptions.ResourceNotFoundException;
import com.org.users.users.mapper.CustomerMapper;
import com.org.users.users.repositories.UserRepository;
import com.org.users.users.services.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final StreamBridge streamBridge;

    @Override
    public void registerUser(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = userRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number");
        }

        Customer savedCustomer = userRepository.save(customer);
        sendCommunication(savedCustomer);

    }

    private void sendCommunication(Customer savedCustomer) {
        var userMsgDto = new UserMsgDto(savedCustomer.getCustomerId(), savedCustomer.getName(),
                savedCustomer.getEmailId(), savedCustomer.getMobileNumber());
        logger.info("Sending communication request for the details: {}", userMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", userMsgDto);
        logger.info("Is the communication request successfully triggered ? {}", result);

    }

    @Override
    public CustomerDto fetchUser(String mobileNumber) {

        Customer customer = userRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        return CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    }

    @Override
    public boolean updateUser(CustomerDto customerDto) {

        boolean isUpdated = false;
        Customer customer = userRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", customerDto.getMobileNumber())
        );

        customer.setName(customerDto.getName());
        customer.setEmailId(customerDto.getEmailId());
        customer.setMobileNumber(customerDto.getMobileNumber());

        Customer savedCustomer = userRepository.save(customer);
        isUpdated = true;
        return isUpdated;
    }

    @Transactional
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = userRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        userRepository.deleteByCustomerId(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long userId) {
        boolean isUpdated = false;
        if(userId != null) {
            Customer customer = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "userId", userId.toString())
            );

            customer.setCommunicationSw(true);
            userRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }


}
