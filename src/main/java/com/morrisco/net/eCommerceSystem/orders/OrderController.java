package com.morrisco.net.eCommerceSystem.orders;

import com.morrisco.net.eCommerceSystem.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{orderId}")
    public OrderDto getOrder(
            @PathVariable(name = "orderId") Long orderId
    ){
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return  ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
