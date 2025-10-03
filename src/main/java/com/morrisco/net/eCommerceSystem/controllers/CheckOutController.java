package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.dtos.ErrorDto;
import com.morrisco.net.eCommerceSystem.entities.Order;
import com.morrisco.net.eCommerceSystem.entities.OrderItems;
import com.morrisco.net.eCommerceSystem.entities.OrderStatus;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.repositories.OrderRepository;
import com.morrisco.net.eCommerceSystem.services.AuthService;
import com.morrisco.net.eCommerceSystem.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
   private final CartService service;
   private AuthService authService;
    private final OrderRepository orderRepository;

    @PostMapping()
    public ResponseEntity<?>  checkOut(
            @RequestBody CheckOutRequest request
            ){

//        var jwt = jwtService.parseToken(authHeader);
//        jwt.isExpired()
//        var userId =jwt.getUserIdFromToken();
//       var user= userService.getUserById(userId);
      var cart = service.getCart(request.getCartId());
      if (service.isEmpty(cart.getId()))
          return ResponseEntity.badRequest().body(new ErrorDto("Cart is Empty"));

      var order= new Order();
      order.setCustomer(authService.getCurentLoggedUser());
      order.setStatus(OrderStatus.PENDING);
      order.setPrice(cart.getTotalPrice());

      cart.getItems().forEach(item->{
          var orederItem = new OrderItems();
          orederItem.setProduct(item.getProduct());
          orederItem.setQuantity(BigDecimal.valueOf(item.getQuantity()));
          orederItem.setOrder(order);
          orederItem.setUnitPrice(item.getProduct().getPrice());
          orederItem.setTotalPrice(item.getTotalPrice());

          order.getOrderedItems().add(orederItem);
      });

     orderRepository.save(order);

     service.clearCart(cart.getId());
      return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not available"));
    }

}
