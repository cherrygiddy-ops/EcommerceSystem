package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.entities.OrderItem;
import com.morrisco.net.eCommerceSystem.entities.PaymentStatus;
import com.morrisco.net.eCommerceSystem.exceptions.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway{

    @Value("${websiteUrl}")
    private String websiteUrl;
    @Value("${stripe.webhooksecretkey}")
    private String weBhookSecretKey;

    @Override
    public CheckOutSession createCheckoutSession(Order order) {
        //creating a session
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-session?orderId =" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("order_id",order.getId().toString());
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

    @Override
    public Optional<PaymentResults> parseWebHookResults(WebHookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getSignature().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, weBhookSecretKey);

            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResults(extractOrderIdFromEvent(event), PaymentStatus.PAID));

                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResults(extractOrderIdFromEvent(event), PaymentStatus.FAILED));

                default -> Optional.empty();
            };
        } catch (SignatureVerificationException e) {
            throw  new PaymentException("invalid signature");
        }
    }

    private Long extractOrderIdFromEvent (Event event){
        var stripeObjects =event.getDataObjectDeserializer().getObject().orElseThrow(()->new PaymentException("Could not deserialzed Stripe Event.Check the SDK version or the API "));
        var paymentIntent =(PaymentIntent)stripeObjects;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
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
