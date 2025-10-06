package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.dtos.ErrorDto;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.services.CheckOutService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService service;
    @PostMapping()
    public ResponseEntity<?>  checkOut(
            @RequestBody CheckOutRequest request
            ){
        try {
            return ResponseEntity.ok(service.checkout(request));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Error Creating a Checkout Session"));
        }
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException( Exception ex){
        return  ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

}
