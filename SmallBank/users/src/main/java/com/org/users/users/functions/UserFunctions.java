package com.org.users.users.functions;

import com.org.users.users.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class UserFunctions {

    public static final Logger logger = LoggerFactory.getLogger(UserFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IUserService userService) {
        return userId -> {
            logger.info("Updating communication status for the user ID : " + userId);
            userService.updateCommunicationStatus(userId);
        };
    }
}
