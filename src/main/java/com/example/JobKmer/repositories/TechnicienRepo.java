package com.example.JobKmer.repositories;

import com.example.JobKmer.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicienRepo extends JpaRepository<Technicien,Long> {
}
