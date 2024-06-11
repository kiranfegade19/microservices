package com.org.users.users.services;

import com.org.users.users.dto.CustomerDto;

public interface IUserService {

    public void registerUser(CustomerDto customerDto);

    public CustomerDto fetchUser(String mobileNumber);

    public boolean updateUser(CustomerDto customerDto);

    public boolean deleteAccount(String mobileNumber);

    boolean updateCommunicationStatus(Long userId);

}
