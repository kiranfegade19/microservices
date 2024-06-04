package com.org.users.users.controllers;

import com.org.users.users.dto.ConfigurationEnvironmentCheckDto;
import com.org.users.users.dto.CustomerDto;
import com.org.users.users.dto.ResponseDto;
import com.org.users.users.services.IUserService;
import com.org.users.users.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.org.users.users.constants.UserConstants.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserController {

    private final IUserService iUserService;

    private final ConfigurationEnvironmentCheckDto configurationEnvironmentCheckDto;

    @GetMapping(value = "/test", produces = MediaType.ALL_VALUE)
    public String testApi(){
        return "Your test is successful : " + configurationEnvironmentCheckDto.getEnvApplicationConfiguration();
    }


    @PostMapping
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody CustomerDto customerDto) {

        iUserService.registerUser(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchUser(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        CustomerDto customerDto = iUserService.fetchUser(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {

        boolean updated = iUserService.updateUser(customerDto);

        if(updated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_417));
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteUser(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {

        boolean deleted = iUserService.deleteAccount(mobileNumber);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_417));
        }
    }
}
