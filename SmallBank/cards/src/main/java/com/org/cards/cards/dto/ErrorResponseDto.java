package com.org.cards.cards.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {

    private String apiPath;
    private String statusCode;
    private String statusMessage;
    private LocalDateTime errorTime;

}