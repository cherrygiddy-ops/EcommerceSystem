package com.morrisco.net.eCommerceSystem.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader=request.getHeader("Authorization");

        if (authHeader ==null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.replace("Bearer ","");
       var jwt= service.parseToken(token);
        if(jwt ==null ||jwt.isExpired()){
            filterChain.doFilter(request,response);
            return;
        }
        /**
         *  new UsernamePasswordAuthenticationToken() -> its contructor is used for creating
         *  1.Anonymous User
         *  2.Authentic User
         */
        var authentication = new UsernamePasswordAuthenticationToken(
                jwt.getUserIdFromToken(),null, List.of(new SimpleGrantedAuthority("ROLE_" + jwt.getRoleFromToken()),new SimpleGrantedAuthority("ROLE_CHERRY")));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//attaching metadata to authentication object like IP address


        SecurityContextHolder.getContext().setAuthentication(authentication);//storing information about the authenticated User

        filterChain.doFilter(request,response);//passing request to the next filter in the chain

    }
}
