package com.org.users.users.mapper;

import com.org.users.users.dto.CustomerDto;
import com.org.users.users.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {

        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setEmailId(customerDto.getEmailId());

        return customer;
    }

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {

        customerDto.setName(customer.getName());
        customerDto.setEmailId(customer.getEmailId());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
}
