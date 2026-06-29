package com.example.JobKmer.dtos;

import com.example.JobKmer.entities.Domaine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicienRequest {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private String ville;
    private String quartier;

    private Domaine domaine;

    private BigDecimal tarifHoraire;
}
