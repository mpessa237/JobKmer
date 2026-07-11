package com.example.JobKmer.services;

import com.example.JobKmer.dtos.LoginRequest;
import com.example.JobKmer.dtos.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        // 1. Authentifier - Lance BadCredentialsException si echec
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getMotDePasse()
        ));

        // 2. Charger l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // 3. Générer le Token JWT
        String token = jwtService.generateToken(userDetails);

        // 4. Retourner la réponse contenant le token
        return new LoginResponse(token);
    }
}
