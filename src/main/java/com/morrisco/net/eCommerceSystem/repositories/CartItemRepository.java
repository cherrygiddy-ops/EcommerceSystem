package com.morrisco.net.eCommerceSystem.repositories;

import com.morrisco.net.eCommerceSystem.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
