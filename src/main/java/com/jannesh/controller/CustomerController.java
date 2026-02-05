package com.jannesh.controller;

import com.jannesh.dto.customer.CustomerDTO;
import com.jannesh.dto.customer.SaveCustomerDTO;
import com.jannesh.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody SaveCustomerDTO requestDTO) {
        return customerService.createCustomer(requestDTO);
    }
}
