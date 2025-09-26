package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.entities.Cart;
import com.morrisco.net.eCommerceSystem.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public List<Cart> getALLCarts(){
    return cartRepository.findAll();
    }
}
