package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDTO;

public interface IAccountsService {

    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchCustomer(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO );

    boolean deleteAccount(String mobileNumber );
}
