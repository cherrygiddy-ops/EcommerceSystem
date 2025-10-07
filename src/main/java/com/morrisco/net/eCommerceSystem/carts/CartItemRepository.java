package com.morrisco.net.eCommerceSystem.carts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<Cart> findByCartId (UUID id);
}
