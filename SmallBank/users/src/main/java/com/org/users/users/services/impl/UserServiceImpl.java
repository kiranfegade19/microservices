package com.org.users.users.services.impl;

import com.org.users.users.dto.CustomerDto;
import com.org.users.users.entity.Customer;
import com.org.users.users.exceptions.CustomerAlreadyExistsException;
import com.org.users.users.exceptions.ResourceNotFoundException;
import com.org.users.users.mapper.CustomerMapper;
import com.org.users.users.repositories.UserRepository;
import com.org.users.users.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;


    @Override
    public void registerUser(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = userRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number");
        }

        Customer savedCustomer = userRepository.save(customer);

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

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = userRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        userRepository.deleteByCustomerId(customer.getCustomerId());
        return true;
    }
}
