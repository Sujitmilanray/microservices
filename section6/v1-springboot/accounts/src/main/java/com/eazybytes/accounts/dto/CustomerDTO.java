package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotEmpty(message = "Name can not be null or empty")
    @Size(max = 30,min = 3, message = "The length of the customer name should  be between 3 to 30")
    private String name;
    @NotEmpty
    @Email(message = "Email address should be a valid value")
    private String email;
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be ten digits")
    private String mobileNumber;
    private AccountsDTO accountsDTO;
}
