package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.repositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void  getCarts(){
        cartRepository.findAll().forEach(System.out::println);
    }
}
