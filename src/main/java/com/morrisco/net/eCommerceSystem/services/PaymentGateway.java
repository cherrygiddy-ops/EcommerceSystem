package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckOutSession createCheckoutSession(Order order);

    Optional<PaymentResults> parseWebHookResults(WebHookRequest request);
}
