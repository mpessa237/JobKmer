package com.example.JobKmer.controllers;

import com.example.JobKmer.dtos.ClientRequest;
import com.example.JobKmer.dtos.LoginRequest;
import com.example.JobKmer.dtos.LoginResponse;
import com.example.JobKmer.dtos.TechnicienRequest;
import com.example.JobKmer.services.AuthService;
import com.example.JobKmer.services.InscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final InscriptionService inscriptionService;
    private final AuthService authService;

    // Inscription Client
    @PostMapping("/inscription")
    public ResponseEntity<String> inscrire(@RequestBody ClientRequest request) {
        inscriptionService.inscription(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Inscription réussie !");
    }

    // Inscription Technicien (Rapatrié ici pour tout centraliser)
    @PostMapping("/inscription-technicien")
    public ResponseEntity<String> inscrireTechnicien(@RequestBody TechnicienRequest request) {
        inscriptionService.inscriptionTechnicien(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Inscription technicien réussie ! Bienvenue sur JobKamer 🔧");
    }

    // Connexion
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // Déconnexion
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Optionnel : extraire le token si tu veux le blacklister plus tard
            String token = authHeader.substring(7);
        }
        
        // Vider le contexte de sécurité de Spring
        SecurityContextHolder.clearContext();
        
        return ResponseEntity.ok("Déconnexion réussie !");
    }

    // Gestion des erreurs locale au contrôleur
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> gererErreur(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
