package com.jannesh.dto.warehouse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jannesh.util.enums.WarehouseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@JsonPropertyOrder({"warehouseId","vendorId","name","contact","email",
        "address1","address2","city","state","country","pincode","status","createdAt","updatedAt"})
public class WarehouseDTO {
    private UUID warehouseId;
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
    private WarehouseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
