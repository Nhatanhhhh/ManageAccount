package com.ra.security.jwt;

import com.ra.security.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${EXPIRED}")
    private Long EXPIRED;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String generateToken(UserPrinciple userPrinciple) {
        Date dateExpiration = new Date(new Date().getTime() + EXPIRED);
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(dateExpiration)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpressionException | ExpiredJwtException | MalformedJwtException exception) {
            logger.error(exception.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
