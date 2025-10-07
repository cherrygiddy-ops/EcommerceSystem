package com.morrisco.net.eCommerceSystem.payments;

import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.repositories.OrderRepository;
import com.morrisco.net.eCommerceSystem.services.AuthService;
import com.morrisco.net.eCommerceSystem.services.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

    public void handleWebHookEvents(WebHookRequest request) {
        paymentGateway.parseWebHookResults(request).ifPresent(paymentResults->{
        var order=orderRepository.findById(paymentResults.getOrderId()).orElseThrow();
        order.setStatus(paymentResults.getPaymentStatus());
        orderRepository.save(order);
        });
    }
}
