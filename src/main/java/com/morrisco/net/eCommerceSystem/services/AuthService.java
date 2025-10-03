package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.morrisco.net.eCommerceSystem.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService  implements UserDetailsService{
    private final UserRepository userRepository;

    public User  getCurentLoggedUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId =(long)authentication.getPrincipal();
        var user = userRepository.findById(userId).orElse(null);
        if (user==null)
            throw  new UsernameNotFoundException("can not find the user");

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user Not Found Exception"));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), Collections.emptyList());
    }
}
