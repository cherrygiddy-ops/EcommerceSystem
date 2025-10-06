package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.entities.Order;

public interface PaymentGateway {
    CheckOutSession createCheckoutSession(Order order);
}
