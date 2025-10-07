package com.morrisco.net.eCommerceSystem.carts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

   @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto (Cart cart);

    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")//mapping a method value to a field
    CartItemDto toDto(CartItem cartItem);


}
