package com.org.users.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String apiPath;
    private HttpStatus statusCode;
    private String statusMessage;
    private LocalDateTime errorTime;

}