package com.example.JobKmer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "demandes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Demande {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Domaine domaineRequis;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    @Column(nullable = false)
    private LocalDate dateCreation = LocalDate.now();

    @Column(precision = 10, scale = 2)
    private BigDecimal budgetEstime;

    // Client qui a soumis la demande
    // Relation ManyToOne : plusieurs demandes pour un client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Technicien assigné (peut être null au départ)
    // Relation ManyToOne : un technicien peut avoir plusieurs demandes
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technicien_id")
    private Technicien technicien;

    // Projet généré si la demande est acceptée (1 à 0..1)
    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    private Projet projet;
}
