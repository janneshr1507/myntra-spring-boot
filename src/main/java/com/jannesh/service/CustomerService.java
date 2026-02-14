package com.jannesh.service;

import com.jannesh.dto.customer.CustomerDTO;
import com.jannesh.dto.customer.SaveCustomerDTO;
import com.jannesh.entity.Customer;
import com.jannesh.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final ModelMapper modelMapper;

    public CustomerDTO createCustomer(SaveCustomerDTO requestDTO) {
        Customer customer = modelMapper.map(requestDTO, Customer.class);
        return modelMapper.map(customerRepo.save(customer), CustomerDTO.class);
    }

    public Customer fetchCustomerByCustomerId(UUID customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));
    }
}
