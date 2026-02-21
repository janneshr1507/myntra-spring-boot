package com.jannesh.util.mapper;

import com.jannesh.dto.PaymentDTO;
import com.jannesh.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(Payment payment);
}
