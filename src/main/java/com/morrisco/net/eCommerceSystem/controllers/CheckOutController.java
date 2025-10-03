package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.dtos.ErrorDto;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.services.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService service;
    @PostMapping()
    public CheckoutResponse  checkOut(
            @RequestBody CheckOutRequest request
            ){
      return service.checkout(request);
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException( Exception ex){
        return  ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

}
