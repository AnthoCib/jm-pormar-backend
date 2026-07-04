package com.jmpormar.auth.security;

import com.jmpormar.auth.repository.AdminUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {
    private final AdminUsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String identificador) throws UsernameNotFoundException {
        return repository.findByUsuarioIgnoreCaseOrCorreoIgnoreCase(identificador, identificador)
                .map(AdminPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador no encontrado"));
    }
}
