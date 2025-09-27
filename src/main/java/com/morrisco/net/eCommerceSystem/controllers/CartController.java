package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.AddItemTOCartRequest;
import com.morrisco.net.eCommerceSystem.dtos.CartDto;
import com.morrisco.net.eCommerceSystem.dtos.CartItemDto;
import com.morrisco.net.eCommerceSystem.entities.Cart;
import com.morrisco.net.eCommerceSystem.entities.CartItem;
import com.morrisco.net.eCommerceSystem.mappers.CartMapper;
import com.morrisco.net.eCommerceSystem.repositories.CartRepository;
import com.morrisco.net.eCommerceSystem.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;


    @GetMapping
    public List<CartDto> getALLCarts(){
    return cartRepository.findAll().stream().map(cartMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<CartDto>addCart(UriComponentsBuilder uriComponentsBuilder){
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto1=cartMapper.toDto(cart);

        var uri =uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto1.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto1);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto>addProductsToCart(
                            @PathVariable(name ="cartId" ) UUID cartId,
                             @RequestBody AddItemTOCartRequest request,
                            UriComponentsBuilder uriComponentsBuilder){
     var cart = cartRepository.getItemsWithCart(cartId).orElse(null);
     if (cart ==null)
         return ResponseEntity.badRequest().build();
     var product =productRepository.findById(request.getProductId()).orElse(null);
        if (product ==null)
            return ResponseEntity.badRequest().build();
     var cartItem = cart.getItems().stream()
             .filter(item->item.getProduct().getId().equals(product.getId()))
             .findFirst()
             .orElse(null);
     if (cartItem !=null)
         cartItem .setQuantity(cartItem.getQuantity() +1);
     else {
          cartItem = new CartItem();
         cartItem.setProduct(product);
         cartItem.setQuantity(1);
         cartItem.setCart(cart);

         cart.getItems().add(cartItem);

     }

     cartRepository.save(cart);

     var cartItemDto = cartMapper.toDto(cartItem);

     return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name = "cartId") UUID cartId){
       var cart=cartRepository.getItemsWithCart(cartId).orElse(null);
       if (cart ==null)
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

       var cartDto = cartMapper.toDto(cart);
        return ResponseEntity.status(HttpStatus.FOUND).body(cartDto);
    }
}
