package com.example.JobKmer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "techniciens")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Technicien extends User{

    private String nom;
    private String prenom;
    @Enumerated(EnumType.STRING)
    private Domaine domaine;
    private String telephone;
    @Column(precision = 10, scale = 2)
    private BigDecimal tarifHoraire;
    private boolean disponible = true;
    private String ville;
    private String quartier;
    @Column(nullable = false)
    private double noteMoyenne = 0.0;

    // Demandes assignées à ce technicien
    @OneToMany(mappedBy = "technicien", cascade = CascadeType.ALL)
    private List<Demande> demandes = new ArrayList<>();

    // Évaluations reçues par ce technicien
    @OneToMany(mappedBy = "technicien", cascade = CascadeType.ALL)
    private List<Evaluation> evaluationsRecues = new ArrayList<>();
}
