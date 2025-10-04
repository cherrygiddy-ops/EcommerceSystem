package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.OrderDto;
import com.morrisco.net.eCommerceSystem.mappers.OrderMapper;
import com.morrisco.net.eCommerceSystem.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper mapper;
    private AuthService authService;

    public List<OrderDto> getAllOrders(){
        var user=authService.getCurentLoggedUser();
        var orders= orderRepository.findByCustomer(user);
        return orders.stream().map(mapper::toDto).toList();
    }
}
