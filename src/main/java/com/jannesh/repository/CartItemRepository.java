package com.jannesh.repository;

import com.jannesh.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCart_CartIdAndItem_ItemId(UUID cartId, UUID itemId);
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.item WHERE ci.cart.cartId=:cartId")
    List<CartItem> findByCart_CartIdWithItem(UUID cartId);

    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.item WHERE ci.cartItemId=:cartItemId")
    Optional<CartItem> findByIdWithItem(UUID cartItemId);
}
