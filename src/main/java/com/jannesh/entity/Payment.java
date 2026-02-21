package com.jannesh.entity;

import com.jannesh.util.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class Payment {
    @Id
    private UUID paymentId;

    @Column(nullable = false)
    private UUID cartId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.paymentId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
