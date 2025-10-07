package com.morrisco.net.eCommerceSystem.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class WebHookRequest {
    private Map<String ,String> signature;
    private String payload;
}
