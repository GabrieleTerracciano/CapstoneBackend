package com.example.CapstoneBackend.Security;


import com.example.CapstoneBackend.Entity.User;
import com.example.CapstoneBackend.Exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.duration}")
    private long duration;

    public String createToken(User user) {
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + duration)).
                subject(String.valueOf(user.getEmail())).
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).
                compact();
    }

    public void verifyToken(String token) throws UnauthorizedException {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                    build().parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Error in authorization, relogin!");
        }
    }

    public String getEmailFromToken(String token) throws UnauthorizedException {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token scaduto, effettua di nuovo il login!");
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("Token JWT non valido!");
        } catch (SignatureException e) {
            throw new UnauthorizedException("Errore di firma del token!");
        } catch (Exception e) {
            throw new UnauthorizedException("Errore inatteso durante la verifica del token!");
        }
    }



}
