package com.org.users.users.services;

import com.org.users.users.dto.UserDetailsDto;
import org.springframework.stereotype.Service;


public interface IUserDetailsService {

    UserDetailsDto fetchUserDetails(String mobileNumber);
}
