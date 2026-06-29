package com.example.JobKmer.configurations;

import com.example.JobKmer.repositories.ClientRepo;
import com.example.JobKmer.repositories.TechnicienRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepo clientRepo;
    private final TechnicienRepo technicienRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        if (clientRepo.findByEmail(userEmail).isPresent()) {
        return this.clientRepo.findByEmail(userEmail).get();
        }

        return technicienRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + userEmail));

        // Si ni Client ni Technicien → HTTP 401

    }
}
