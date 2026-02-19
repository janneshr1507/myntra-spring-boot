package com.jannesh.repository;

import com.jannesh.entity.Cart;
import com.jannesh.util.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItemList WHERE c.customer.customerId = :customerId AND c.status = :cartStatus")
    Optional<Cart> findByCustomer_CustomerIdAndStatus(UUID customerId, CartStatus cartStatus);

    boolean existsByCustomer_CustomerIdAndStatus(UUID customerId, CartStatus cartStatus);

}
