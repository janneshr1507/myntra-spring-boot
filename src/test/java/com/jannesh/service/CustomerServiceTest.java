package com.jannesh.service;

import com.jannesh.dto.customer.CustomerDTO;
import com.jannesh.entity.Customer;
import com.jannesh.repository.CustomerRepository;
import com.jannesh.util.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void shouldSuccessfullyFetchCustomerDetails() {
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        when(customerRepo.findById(any(UUID.class))).thenReturn(Optional.of(customer));
        when(mapper.toDTO(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(savedCustomer.getCustomerId());
            return customerDTO;
        });

        CustomerDTO savedCustomer = customerService.fetchCustomerDTOByCustomerId(customerId);

        assertEquals(customerId, savedCustomer.getCustomerId());

    }
}
