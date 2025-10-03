package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.config.JwtConfig;
import com.morrisco.net.eCommerceSystem.dtos.JwtResponse;
import com.morrisco.net.eCommerceSystem.dtos.LoginRequest;
import com.morrisco.net.eCommerceSystem.dtos.UserDto;
import com.morrisco.net.eCommerceSystem.mappers.UserMapper;
import com.morrisco.net.eCommerceSystem.repositories.UserRepository;
import com.morrisco.net.eCommerceSystem.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
   private final UserMapper userMapper;
   private final JwtConfig config;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

      var user= userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
       var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie  = new Cookie("refreshToken",refreshToken.toString());
        cookie.setPath("/auth/refresh");
        cookie.setHttpOnly(true);//only accessible via Http
        cookie.setMaxAge(config.getRefreshTokenExpiration());
        cookie.setSecure(true);//sends over encrpted http channel

        response.addCookie(cookie);
       
       return ResponseEntity.ok(new JwtResponse(accessToken.toString()));

    }

    @PostMapping("/validate")
    public boolean  validateToken(@RequestHeader("Authorization") String authHeader){

      var token = authHeader.replace("Bearer ","");

      var jwt = jwtService.parseToken(token);
        return jwt.isExpired();
    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> currentUser (){
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var userId =(long)authentication.getPrincipal();
    var user = userRepository.findById(userId).orElse(null);
     if (user==null)
         return ResponseEntity.notFound().build();
     var userDto = userMapper.toDto(user);

     return ResponseEntity.ok(userDto);
    }
   @PostMapping("/refresh")
   public ResponseEntity<JwtResponse>refresh(
           @CookieValue(value = "refreshToken") String refreshToken
   ){
        var jwt = jwtService.parseToken(refreshToken);
    if(jwt.isExpired())
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    var userId =jwt.getUserIdFromToken();
    var user = userRepository.findById(userId).orElseThrow();
    var accessToken =jwtService.generateAccessToken(user);

    return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
   }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleUserFound(){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}