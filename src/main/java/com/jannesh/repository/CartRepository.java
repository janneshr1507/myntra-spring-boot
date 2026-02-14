package com.jannesh.repository;

import com.jannesh.dto.cart.CartDTO;
import com.jannesh.dto.cart.SaveCartDTO;
import com.jannesh.entity.Cart;
import com.jannesh.util.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomer_CustomerIdAndStatus(UUID customerId, CartStatus cartStatus);
    boolean existsByCustomer_CustomerIdAndStatus(UUID customerId, CartStatus cartStatus);
}
