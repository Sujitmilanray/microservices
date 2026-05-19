package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Since the MobileNumber matches with the field name present in the Customer entity class
     * so spring data JPA framework can fetch the record using this column value provided field name should match in
     * both the cases, it's mandatory not case sensitive.
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
