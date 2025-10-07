package com.morrisco.net.eCommerceSystem.payments;

import com.morrisco.net.eCommerceSystem.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResults {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
