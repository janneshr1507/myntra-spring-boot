package com.jannesh.service;

import com.jannesh.dto.customer.CustomerDTO;
import com.jannesh.dto.customer.SaveCustomerDTO;
import com.jannesh.entity.Customer;
import com.jannesh.repository.CustomerRepository;
import com.jannesh.util.enums.CustomerStatus;
import com.jannesh.util.mapper.CustomerMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final CustomerMapper mapper;

    public CustomerDTO createCustomer(SaveCustomerDTO requestDTO) {
        Customer customer = mapper.toEntity(requestDTO);
        return mapper.toDTO(customerRepo.save(customer));
    }

    public Customer fetchCustomerByCustomerId(UUID customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));
    }

    public CustomerDTO fetchCustomerDTOByCustomerId(UUID customerId) {
        Customer customer = fetchCustomerByCustomerId(customerId);
        return mapper.toDTO(customer);
    }

    public boolean existsByCustomerId(UUID customerId) {
        return customerRepo.existsByCustomerIdAndCustomerStatus(customerId, CustomerStatus.ACTIVE);
    }
}
