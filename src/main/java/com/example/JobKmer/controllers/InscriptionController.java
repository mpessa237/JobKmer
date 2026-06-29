package com.example.JobKmer.controllers;

import com.example.JobKmer.dtos.ClientRequest;
import com.example.JobKmer.dtos.TechnicienRequest;
import com.example.JobKmer.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService ;

    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestBody ClientRequest clientRequest){
        inscriptionService.inscription(clientRequest);
        return ResponseEntity.ok("inscription reussie avec succes!!");


    }

    @PostMapping("/inscription-technicien")
    public ResponseEntity<String> inscriptionTechnicien(
            @RequestBody TechnicienRequest request) {
        inscriptionService.inscriptionTechnicien(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Inscription technicien réussie ! Bienvenue sur JobKamer 🔧");
    }

    // Gestion des erreurs
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> gererErreur(
            IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}

