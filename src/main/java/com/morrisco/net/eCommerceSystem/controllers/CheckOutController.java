package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.dtos.ErrorDto;
import com.morrisco.net.eCommerceSystem.entities.OrderStatus;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.exceptions.PaymentException;
import com.morrisco.net.eCommerceSystem.repositories.OrderRepository;
import com.morrisco.net.eCommerceSystem.services.CheckOutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService service;

    @Value("${stripe.webhooksecretkey}")
    private String weBhookSecretKey;

    private final OrderRepository orderRepository;

    @PostMapping()
    public CheckoutResponse checkOut(@RequestBody CheckOutRequest request){
            return service.checkout(request);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handlewebhook(
            @RequestHeader("Stripe-Signature") String signature,//Used to Verify That the request came from stripe not tampered with
            @RequestBody String payload
    ){
            try {
               var event= Webhook.constructEvent(payload,signature,weBhookSecretKey);
               var stripeObjects =event.getDataObjectDeserializer().getObject().orElse(null);
                switch (event.getType()){
                    case "payment_intent.succeeded" ->{
                        var paymentIntent =(PaymentIntent)stripeObjects;
                        if (paymentIntent != null){
                            var orderId=paymentIntent.getMetadata().get("order_id");
                            var order=orderRepository.findById(Long.valueOf(orderId)).orElseThrow();
                            order.setStatus(OrderStatus.CANCELLED);
                            orderRepository.save(order);
                        }
                    }
                    case "payment_intent.failed" ->{
                        //update the status as failed
                    }
                }
                return ResponseEntity.ok().build();
            } catch (SignatureVerificationException e) {
                return ResponseEntity.badRequest().build();
            }

    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException( Exception ex){
        return  ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException( Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error Creating a Checkout Session"));
    }

}
