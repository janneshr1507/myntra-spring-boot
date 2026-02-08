package com.jannesh.repository;

import com.jannesh.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    boolean existsByVendor_VendorIdAndPincode(UUID vendorId, String pincode);
    boolean existsByVendor_VendorId(UUID vendorId);
    List<Warehouse> findByVendor_VendorId(UUID vendorId);
}
