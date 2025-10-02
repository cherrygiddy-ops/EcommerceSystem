package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.crypto.SecretKey;
import java.util.Date;

@ToString
@AllArgsConstructor
public class Jwt {
    private Claims claims;
    private SecretKey secretKey;


    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }

    public Integer getUserIdFromToken(){
        return Integer.valueOf(claims.getSubject());
    }
    public Role getRoleFromToken(){
        return Role.valueOf(claims.get("role",String.class));
    }

    public  String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
