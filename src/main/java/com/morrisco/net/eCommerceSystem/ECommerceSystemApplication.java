package com.morrisco.net.eCommerceSystem;

import com.morrisco.net.eCommerceSystem.services.CartService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ECommerceSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(ECommerceSystemApplication.class, args);
       var cart=context.getBean(CartService.class);
       //cart.getCarts();
	}

}
