package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.entities.OrderItem;
import com.morrisco.net.eCommerceSystem.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentGateway implements PaymentGateway{

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Override
    public CheckOutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-session?orderId =" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel");
            order.getItems().forEach(item -> {
                        var lineItem = createLineItem(item);
                        builder.addLineItem(lineItem);
            });

            var sesion = Session.create(builder.build());

            var checkoutUrl = sesion.getUrl();
            return new CheckOutSession(checkoutUrl);
        }catch (StripeException ex){
            System.out.println(ex.getMessage());//use the Loggin service i.e  Folks Building Sentry
            throw new PaymentException();
        }
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(String.valueOf(item.getQuantity())))
                .setPriceData(createPriceData(item)).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("kes")
                .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(10000)))
                .setProductData(createProductData(item)).build();
    }

    private  SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName()).build();
    }
}
