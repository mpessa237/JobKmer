package com.example.JobKmer.repositories;

import com.example.JobKmer.entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeRepo extends JpaRepository<Demande,Long> {
}
