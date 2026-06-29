package com.example.JobKmer.services;

import com.example.JobKmer.dtos.ClientRequest;
import com.example.JobKmer.dtos.TechnicienRequest;
import com.example.JobKmer.entities.Client;
import com.example.JobKmer.entities.Role;
import com.example.JobKmer.entities.Technicien;
import com.example.JobKmer.entities.User;
import com.example.JobKmer.repositories.ClientRepo;
import com.example.JobKmer.repositories.TechnicienRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final ClientRepo clientRepo;
    private final TechnicienRepo technicienRepo;
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


    public void inscriptionTechnicien(TechnicienRequest request) {

        if (technicienRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Cet email est déjà utilisé !");
        }

        if (clientRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "Cet email est déjà utilisé par un Client !");
        }

        if (request.getMotDePasse().length() < 8) {
            throw new IllegalArgumentException(
                    "Le mot de passe doit contenir au moins 8 caractères !");
        }


        Technicien technicien = new Technicien();
        technicien.setNom(request.getNom());
        technicien.setPrenom(request.getPrenom());
        technicien.setEmail(request.getEmail());
        technicien.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        technicien.setTelephone(request.getTelephone());
        technicien.setVille(request.getVille());
        technicien.setQuartier(request.getQuartier());
        technicien.setDomaine(request.getDomaine());
        technicien.setTarifHoraire(request.getTarifHoraire());

        // Champs gérés côté serveur — jamais depuis le client
        technicien.setRole(Role.TECHNICIEN);
        technicien.setActif(true);
        technicien.setDisponible(true);
        technicien.setNoteMoyenne(0.0);

        technicienRepo.save(technicien);

    }

}