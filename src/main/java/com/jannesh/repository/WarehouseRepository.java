package com.jannesh.repository;

import com.jannesh.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    boolean existsByVendor_VendorIdAndPincode(UUID vendorId, String pincode);
    List<Warehouse> findByVendor_VendorId(UUID vendorId);
    Optional<Warehouse> findByWarehouseIdAndVendor_VendorId(UUID warehouseId, UUID vendorId);
}
