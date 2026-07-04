package com.jmpormar.auth.security;

import com.jmpormar.auth.entity.AdminUsuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class AdminPrincipal implements UserDetails {
    private final UUID idAdmin;
    private final String usuario;
    private final String correo;
    private final String password;
    private final boolean activo;

    public AdminPrincipal(AdminUsuario admin) {
        this.idAdmin = admin.getIdAdmin();
        this.usuario = admin.getUsuario();
        this.correo = admin.getCorreo();
        this.password = admin.getPasswordHash();
        this.activo = admin.isActivo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
