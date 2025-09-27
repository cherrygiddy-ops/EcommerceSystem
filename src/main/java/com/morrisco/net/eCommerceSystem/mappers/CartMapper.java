package com.morrisco.net.eCommerceSystem.mappers;

import com.morrisco.net.eCommerceSystem.dtos.CartDto;
import com.morrisco.net.eCommerceSystem.dtos.CartItemDto;
import com.morrisco.net.eCommerceSystem.entities.Cart;
import com.morrisco.net.eCommerceSystem.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

   @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto (Cart cart);

    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")//mapping a method value to a field
    CartItemDto toDto(CartItem cartItem);


}
