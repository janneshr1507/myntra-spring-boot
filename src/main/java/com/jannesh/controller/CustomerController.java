package com.jannesh.controller;

import com.jannesh.dto.customer.CustomerDTO;
import com.jannesh.dto.customer.SaveCustomerDTO;
import com.jannesh.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get/{customerId}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable("customerId")UUID customerId) {
        return new ResponseEntity<>(customerService.fetchCustomerDTOByCustomerId(customerId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody SaveCustomerDTO requestDTO) {
        return customerService.createCustomer(requestDTO);
    }
}
