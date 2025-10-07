package com.morrisco.net.eCommerceSystem.auth;

import com.morrisco.net.eCommerceSystem.users.UserRepository;
import lombok.AllArgsConstructor;
import com.morrisco.net.eCommerceSystem.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User  getCurentLoggedUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId =(long)authentication.getPrincipal();
        var user = userRepository.findById(userId).orElse(null);
        if (user==null)
            throw  new UsernameNotFoundException("can not find the user");

        return user;
    }


}
