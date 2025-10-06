package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.entities.User;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
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

    @Value("${websiteUrl}")
    private String websiteUrl;


    @Transactional
    public CheckoutResponse checkout (CheckOutRequest request) throws StripeException {
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
        var builder=SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(websiteUrl + "/checkout-session?orderId =" +order.getId())
                .setCancelUrl(websiteUrl + "/checkout-cancel");
        order.getItems().forEach(item->{
                    var lineItem =SessionCreateParams.LineItem.builder()
                            .setQuantity(Long.valueOf(String.valueOf(item.getQuantity())))
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("kes")
                                    .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(10000)))
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(item.getProduct().getName()).build()).build()).build();
                    builder.addLineItem(lineItem);

                }
        );

        var sesion=Session.create(builder.build());

        var checkoutUrl=sesion.getUrl();

        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId(),checkoutUrl);
    }catch (StripeException ex){
        System.out.println(ex.getMessage());
       orderRepository.delete(order);
        throw  ex;
    }
    }
}
