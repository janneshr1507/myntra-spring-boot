package com.jannesh.dto.customer;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jannesh.util.enums.CustomerStatus;
import com.jannesh.util.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@JsonPropertyOrder({"customerId","fullName","mobileNumber","email","gender","customerStatus","createdAt","updatedAt"})
public class CustomerDTO {
    private UUID customerId;
    private String fullName;
    private String mobileNumber;
    private String email;
    private Gender gender;
    private CustomerStatus customerStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
