package com.org.cards.cards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {

    @NotEmpty(message = "Name cannot be Null or Empty")
    @Size(min = 5, max = 30, message = "The name of the customer must be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email address cannot be Null or Empty")
    @Email(message = "Email address must be a valid value")
    private String emailId;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

}
