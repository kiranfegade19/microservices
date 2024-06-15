package com.org.cards.cards.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseDto {

    private String apiPath;
    private String statusCode;
    private String statusMessage;
    private LocalDateTime errorTime;

}