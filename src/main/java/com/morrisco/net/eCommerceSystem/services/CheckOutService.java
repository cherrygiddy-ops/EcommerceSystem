package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.entities.User;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.exceptions.PaymentException;
import com.morrisco.net.eCommerceSystem.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CheckOutService {
    private final CartService cartService;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;



    @Transactional
    public CheckoutResponse checkout (CheckOutRequest request) {
        var cart = cartService.getCart(request.getCartId());
        if (cart ==null)
            throw new CartNotFoundException();
        if (cart.isEmpty())
            throw new CartEmptyException();
            //return ResponseEntity.badRequest().body(new ErrorDto("Cart is Empty"));

        var order = Order.createOrderFromCart(cart,authService.getCurentLoggedUser());

        orderRepository.save(order);

    try {
        //create a session
       var session= paymentGateway.createCheckoutSession(order);
       var checkoutUrl =session.getCheckOutUrl();
        cartService.clearCart(cart.getId());
        return new CheckoutResponse(order.getId(),checkoutUrl);
    }catch (PaymentException ex){
       orderRepository.delete(order);
        throw  ex;
    }
    }
}
