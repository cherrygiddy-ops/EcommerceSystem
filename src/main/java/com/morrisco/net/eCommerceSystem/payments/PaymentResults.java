package com.morrisco.net.eCommerceSystem.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResults {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
