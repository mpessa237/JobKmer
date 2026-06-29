package com.example.JobKmer.repositories;

import com.example.JobKmer.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicienRepo extends JpaRepository<Technicien,Long> {

    Optional<Technicien> findByEmail(String email);

    boolean existsByEmail(String email);
}
