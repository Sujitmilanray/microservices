package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class AccountsDTO {
    @NotEmpty(message = "Account Number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}]", message = "Account Number must be ten digits")
    private Long accountNumber;
    @NotEmpty(message = "Account Type can not be null or empty")
    private String accountType;
    @NotEmpty(message = "Branch Address can not be null or empty")
    private String branchAddress;
}
