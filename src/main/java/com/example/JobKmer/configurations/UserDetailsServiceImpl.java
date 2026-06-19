package com.example.JobKmer.configurations;

import com.example.JobKmer.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return this.clientRepo.findByEmail(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException("client non trouve!!"));
    }
}
