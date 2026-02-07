package com.jannesh.dto.warehouse;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class SaveWarehouseDTO {
    private UUID vendorId;
    private String name;
    private String contact;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
