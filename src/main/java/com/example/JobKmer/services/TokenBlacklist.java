package com.example.JobKmer.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklist {

    private final Set<String> blacklistedToken = new HashSet<>();

    // Revoquer un token
    public void blacklist(String token){
        blacklistedToken.add(token);
    }

    //Vérifier si un token est révoqué
    public boolean isBlacklisted(String token){
        return blacklistedToken.contains(token);
    }

}
