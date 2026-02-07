package com.jannesh.dto.vendor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@ToString
@JsonPropertyOrder({"vendorId","name","createdAt","updatedAt"})
public class VendorDTO {
    private UUID vendorId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
