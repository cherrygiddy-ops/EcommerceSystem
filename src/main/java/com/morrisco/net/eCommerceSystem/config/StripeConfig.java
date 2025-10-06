package com.morrisco.net.eCommerceSystem.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class StripeConfig {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostConstruct //telling spring to initialize this key when project start
    public void init(){
        Stripe.apiKey = secretKey;
    }
}
