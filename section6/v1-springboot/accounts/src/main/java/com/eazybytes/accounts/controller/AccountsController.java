package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;

    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {

        iAccountsService.createAccount(customerDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchCustomerDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        CustomerDTO customerDTO = iAccountsService.fetchCustomer(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(@Valid @RequestBody CustomerDTO customerDTO) {

        boolean isUpdated = iAccountsService.updateAccount(customerDTO);

        if(isUpdated)
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
