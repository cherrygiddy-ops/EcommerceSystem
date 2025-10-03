package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.dtos.ErrorDto;
import com.morrisco.net.eCommerceSystem.entities.Cart;
import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckOutService {
    private final CartService cartService;
    private final AuthService authService;
    private final OrderRepository orderRepository;

    public CheckoutResponse checkout (CheckOutRequest request){
        var cart = cartService.getCart(request.getCartId());
        if (cart ==null)
            throw new CartNotFoundException();
        if (cart.isEmpty())
            throw new CartEmptyException();
            //return ResponseEntity.badRequest().body(new ErrorDto("Cart is Empty"));

        var order = Order.createOrderFromCart(cart,authService.getCurentLoggedUser());

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
