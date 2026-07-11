package com.example.JobKmer.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    //--- Générer un tojen ---
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //--- Valider un token ---
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    //--- Extraire l'email depuis le token ---
    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //--- Vérifié si le token est expiré ---
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }

    //---Extraire n'importe quelle info du token ---
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    //---Construire la clé signature ---
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
