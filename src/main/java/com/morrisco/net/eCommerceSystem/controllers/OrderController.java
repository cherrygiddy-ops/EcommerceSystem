package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.OrderDto;
import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
   private OrderService orderService;
    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }
}
