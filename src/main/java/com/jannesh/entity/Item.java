package com.jannesh.entity;

import com.jannesh.util.enums.ItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
@Getter @Setter
public class Item {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false, name = "item_size")
    private String size;

    @Column(nullable = false)
    private String fit;

    @Column(nullable = false)
    private String material;

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal actualPrice;

    @Column(precision = 3, scale = 2)
    private BigDecimal discount;

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal sellingPrice;

    @Column(precision = 2, scale = 1)
    private BigDecimal averageRatings;

    private Long totalNumberOfRatings;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus itemStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.itemId = UUID.randomUUID();
        this.itemStatus = ItemStatus.ACTIVE;
        this.sellingPrice = this.actualPrice.subtract(this.actualPrice.multiply(this.discount)).setScale(2, RoundingMode.HALF_UP);
        this.averageRatings = BigDecimal.valueOf(0.0);
        this.totalNumberOfRatings = 0L;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventoryList = new ArrayList<>();
}
