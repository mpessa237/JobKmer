package com.example.JobKmer.configurations;

import com.example.JobKmer.services.JwtService;
import com.example.JobKmer.services.TokenBlacklist;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklist tokenBlacklist;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Lire le header Authorization
        final String authHeader = request.getHeader("Authorization");

        // 2. Pas de token ou de mauvais format → passer au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraire le token (enlver "Bearer")
        final String token = authHeader.substring(7);

        // Vérifie si le token est dans la listeNoire ou révoqu
        if(tokenBlacklist.isBlacklisted(token)){
            filterChain.doFilter(request, response);
            return;
        }
        final String email =  jwtService.extractEmail(token);

        // 4. Si email est valide et pas encore authentifié
        if (email != null && SecurityContextHolder.getContext()
                .getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 5. Valider le token (signature + expiration)
            if (jwtService.isTokenValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Enregistrement de l'authentification dans le contexte
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        // 7. Passer au filtre suivant (puis au Controller)
        filterChain.doFilter(request, response);
    }

}
