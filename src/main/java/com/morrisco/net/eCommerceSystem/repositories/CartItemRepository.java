package com.morrisco.net.eCommerceSystem.repositories;

import com.morrisco.net.eCommerceSystem.entities.Cart;
import com.morrisco.net.eCommerceSystem.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<Cart> findByCartId (UUID id);
}
