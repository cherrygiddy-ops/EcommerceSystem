package com.morrisco.net.eCommerceSystem.auth.jwt;

import com.morrisco.net.eCommerceSystem.users.Role;
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

    public Long getUserIdFromToken(){
        return Long.valueOf(claims.getSubject());
    }
    public Role getRoleFromToken(){
        return Role.valueOf(claims.get("role",String.class));
    }

    public  String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
