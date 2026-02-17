package com.jannesh.repository;

import com.jannesh.entity.Customer;
import com.jannesh.util.enums.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByCustomerIdAndCustomerStatus(UUID customerId, CustomerStatus customerStatus);
}
