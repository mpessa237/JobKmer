package com.example.JobKmer.controllers;

import com.example.JobKmer.dtos.ClientRequest;
import com.example.JobKmer.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
