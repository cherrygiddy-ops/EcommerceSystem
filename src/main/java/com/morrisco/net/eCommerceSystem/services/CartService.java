package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.CartDto;
import com.morrisco.net.eCommerceSystem.dtos.CartItemDto;
import com.morrisco.net.eCommerceSystem.entities.Cart;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.exceptions.ProductNotFoundException;
import com.morrisco.net.eCommerceSystem.mappers.CartMapper;
import com.morrisco.net.eCommerceSystem.repositories.CartRepository;
import com.morrisco.net.eCommerceSystem.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;


    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }
    public CartItemDto addProductToCart(UUID cartId,Long productId){
        var cart = cartRepository.getItemsWithCart(cartId).orElse(null);
        if (cart ==null)
            throw  new CartNotFoundException();
        var product =productRepository.findById(productId).orElse(null);
        if (product ==null)
            throw new ProductNotFoundException();
        var cartItem =cart.addToCart(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public   List<CartDto> getAllCarts() {
        return cartRepository.findAll().stream().map(cartMapper::toDto).toList();
    }
    public Cart getCart(UUID cartId){
        var cart=cartRepository.getItemsWithCart(cartId).orElse(null);
        if (cart ==null)
            throw  new CartNotFoundException();
     return cart;
    }

    public CartItemDto updateCartItem(UUID cartId,Long productID,int quantity){
        var cart=cartRepository.getItemsWithCart(cartId).orElse(null);
        if (cart ==null)
            throw new CartNotFoundException();
        var cartItem = cart.filterItem(productID);
        if (cartItem==null)
            throw new ProductNotFoundException();
        cartItem.setQuantity(BigDecimal.valueOf(quantity));
        cartRepository.save(cart);

      return cartMapper.toDto(cartItem);
    }
    public void deleteCartItem(UUID cartId, Long productID) {
        var cart=cartRepository.getItemsWithCart(cartId).orElse(null);
        if (cart ==null)
            throw new CartNotFoundException();
        var cartItem = cart.filterItem(productID);
        if (cartItem==null)
            throw new ProductNotFoundException();
        cart.deleteItem(productID);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart=cartRepository.getItemsWithCart(cartId).orElse(null);
        if (cart ==null)
            throw new CartNotFoundException();
        cart.deleteAllItems();
        cartRepository.save(cart);
    }

//    public boolean isEmpty(UUID carId){
//        var cart=cartRepository.getItemsWithCart(carId).orElse(null);
//        if (cart ==null)
//            throw new CartNotFoundException();
//        return cart.getItems().isEmpty();
//    }
}
