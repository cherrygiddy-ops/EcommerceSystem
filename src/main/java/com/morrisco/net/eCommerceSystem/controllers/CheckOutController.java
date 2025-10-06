package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.CheckOutRequest;
import com.morrisco.net.eCommerceSystem.dtos.CheckoutResponse;
import com.morrisco.net.eCommerceSystem.dtos.ErrorDto;
import com.morrisco.net.eCommerceSystem.exceptions.CartEmptyException;
import com.morrisco.net.eCommerceSystem.exceptions.CartNotFoundException;
import com.morrisco.net.eCommerceSystem.exceptions.PaymentException;
import com.morrisco.net.eCommerceSystem.services.CheckOutService;
import com.morrisco.net.eCommerceSystem.services.WebHookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService service;


    @PostMapping()
    public CheckoutResponse checkOut(@RequestBody CheckOutRequest request){
            return service.checkout(request);
    }

    @PostMapping("/webhook")
    public void handlewebhook(@RequestHeader()Map<String,String> headers, @RequestBody String payload){
         service.handleWebHookEvents(new WebHookRequest(headers, payload));
    }



    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException( Exception ex){
        return  ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException( Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error Creating a Checkout Session"));
    }

}
