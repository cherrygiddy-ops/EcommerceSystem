package com.morrisco.net.eCommerceSystem.payments;

import com.morrisco.net.eCommerceSystem.orders.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckOutSession createCheckoutSession(Order order);

    Optional<PaymentResults> parseWebHookResults(WebHookRequest request);
}
