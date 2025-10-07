package com.morrisco.net.eCommerceSystem.carts;


import com.morrisco.net.eCommerceSystem.products.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@Tag(name = "Carts")
public class CartController {
    
    private final CartService cartService;
    private  final CartMapper cartMapper;


    @GetMapping
    public List<CartDto> getALLCarts(){
    return cartService.getAllCarts();
    }

    @PostMapping
    public ResponseEntity<CartDto>addCart(UriComponentsBuilder uriComponentsBuilder){
        var cartDto = cartService.createCart();

        var uri =uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    

    @PostMapping("/{cartId}/items")
    @Operation(summary = "add a Product to Cart")
    public ResponseEntity<CartItemDto>addProductsToCart(
                           @Parameter(description = "the id of the cart")
                            @PathVariable(name ="cartId" ) UUID cartId,
                             @RequestBody AddItemTOCartRequest request){
     var cartItemDto= cartService.addProductToCart(cartId, request.getProductId());
     return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable(name = "cartId") UUID cartId){
        var cart= cartService.getCart(cartId);
       return cartMapper.toDto(cart);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateItem(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productID,
            @Valid  @RequestBody UpdateCartItemRequest request
    ){
      return cartService.updateCartItem(cartId,productID,request.getQuantity());

    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem (
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productID
    ){
       cartService.deleteCartItem(cartId,productID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(
            @PathVariable(name = "cartId") UUID cartId
    ){
       cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>>handleCartNotFound(){
    return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not available"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>>handleProductNotFound(){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Product not Found in Cart"));
    }
}
