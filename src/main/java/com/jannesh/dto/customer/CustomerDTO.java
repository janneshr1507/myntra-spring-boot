package com.jannesh.dto.customer;

import com.jannesh.util.enums.CustomerStatus;
import com.jannesh.util.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class CustomerDTO {
    private UUID customerId;
    private String fullName;
    private String mobileNumber;
    private String email;
    private Gender gender;
    private CustomerStatus customerStatus;
}
