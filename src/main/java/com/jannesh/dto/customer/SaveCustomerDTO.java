package com.jannesh.dto.customer;

import com.jannesh.util.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveCustomerDTO {
    private String fullName;
    private String mobileNumber;
    private String email;
    private Gender gender;
}
