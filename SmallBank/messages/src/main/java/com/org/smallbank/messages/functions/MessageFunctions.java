package com.org.smallbank.messages.functions;

import com.org.smallbank.messages.dto.UserMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<UserMsgDto, UserMsgDto> email() {
        return userMsgDto -> {
          logger.info("Sending email with the details : " + userMsgDto.toString());
          return userMsgDto;
        };
    }

    @Bean
    public Function<UserMsgDto, Long> sms() {
        return userMsgDto -> {
            logger.info("Sending sms with the details : " + userMsgDto.toString());
            return userMsgDto.userId();
        };
    }


}
