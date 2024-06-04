package com.org.users.users;

import com.org.users.users.dto.CustomerDto;
import com.org.users.users.dto.ErrorResponseDto;
import com.org.users.users.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Objects;

import static com.org.users.users.constants.UserConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    public void beforeAll() {
        baseUrl = String.format("http://localhost:%d/api/v1/user", port);
    }

    @Test
    @Order(1)
    public void testRegisterUser() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Ramesh Kapoor");
        customerDto.setEmailId("xyz@xyz.com");
        customerDto.setMobileNumber("0123456789");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, headers);

        ResponseEntity<ResponseDto> responseEntity =  restTemplate.postForEntity(baseUrl, request, ResponseDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode() );
        assertEquals(STATUS_201, responseEntity.getBody().getStatusCode());
        assertEquals(MESSAGE_201, responseEntity.getBody().getStatusMessage());
    }

    @Test
    @Order(2)
    public void testRegisterUser_InvalidMobileNumber() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Ramesh Kapoor");
        customerDto.setEmailId("xyz@xyz.com");
        customerDto.setMobileNumber("01234567");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, headers);

        ResponseEntity<HashMap> responseEntity =  restTemplate.postForEntity(baseUrl, request, HashMap.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode() );
        assertEquals("Mobile number must be 10 digits", responseEntity.getBody().get("mobileNumber"));

    }

    @Test
    @Order(3)
    public void testFetchRegisteredUser() {

        String url = baseUrl + "?mobileNumber=0123456789";

        ResponseEntity<CustomerDto> responseEntity =  restTemplate.getForEntity(url, CustomerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
        assertEquals("Ramesh Kapoor", responseEntity.getBody().getName());
        assertEquals("xyz@xyz.com", responseEntity.getBody().getEmailId());
        assertEquals("0123456789", responseEntity.getBody().getMobileNumber());

    }

    @Test
    @Order(4)
    public void testDeleteRegisteredUser() {

        String url = baseUrl + "?mobileNumber=0123456789";

        restTemplate.delete(url, ResponseDto.class);

        ResponseEntity<ErrorResponseDto> responseEntity =  restTemplate.getForEntity(url, ErrorResponseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
        if(Objects.nonNull(responseEntity.getBody().getStatusCode())) {
            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getBody().getStatusCode());
        }

    }
}
