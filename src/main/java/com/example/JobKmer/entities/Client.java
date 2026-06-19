package com.example.JobKmer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User{
    private String nom;
    private String prenom;
    private String telephone;
    private String ville;
    private String quartier;

    // Un client peut soumettre plusieurs demandes
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demande> demandes = new ArrayList<>();

    // Un client peut laisser plusieurs évaluations
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations = new ArrayList<>();
}
