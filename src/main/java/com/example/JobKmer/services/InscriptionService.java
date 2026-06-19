package com.example.JobKmer.services;

import com.example.JobKmer.dtos.ClientRequest;
import com.example.JobKmer.entities.Client;
import com.example.JobKmer.entities.Role;
import com.example.JobKmer.entities.User;
import com.example.JobKmer.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;


    public void inscription(ClientRequest clientRequest) {

        if (clientRepo.findByEmail(clientRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("cet email existe deja!!");
        }

        Client client = new Client();
        client.setNom(clientRequest.getNom());
        client.setPrenom(clientRequest.getPrenom());
        client.setEmail(clientRequest.getEmail());
        client.setMotDePasse(passwordEncoder.encode(clientRequest.getMotDePasse()));
        client.setTelephone(clientRequest.getTelephone());
        client.setVille(clientRequest.getVille());
        client.setQuartier(clientRequest.getQuartier());

        client.setRole(Role.CLIENT);
        client.setActif(true);

        clientRepo.save(client);

    }
}
