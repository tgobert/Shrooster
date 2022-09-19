package com.revature.util;

import com.revature.exceptions.AuthenticationException;
import com.revature.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenManager {
    private final Key key;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtTokenManager() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Generate token
    public String issueToken(User user) {
        return Jwts.builder()
                .setId(String.valueOf(user.getUserId()))
                .setSubject(user.getUserEmail())
                .setIssuer("Rolodex API")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key).compact();
    }

    // Parse token
    public int parseUserIdFromToken(String token) {
        try {
            return Integer.parseInt(Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody().getId());
        } catch (Exception e) {
            logger.warn("JWT error parsing user id from token");
            throw new AuthenticationException("Your session is expired. Please sign in again.");
        }
    }

    public String parseUserEmailFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            logger.warn("JWT error parsing user email from token");
            throw new AuthenticationException("Your session is expired. Please sign in again.");
        }
    }

}
