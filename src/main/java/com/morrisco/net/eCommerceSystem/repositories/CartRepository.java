package com.morrisco.net.eCommerceSystem.repositories;

import com.morrisco.net.eCommerceSystem.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
