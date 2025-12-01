package com.example.bembos.factory;

import org.springframework.stereotype.Component;
import com.example.bembos.dto.UsuarioDTO;
import com.example.bembos.model.Rol;
import com.example.bembos.model.Usuario;
import com.example.bembos.repository.RolRepository;

@Component
public class UsuarioFactory {

    private final String DEFAULT_ROL_NAME = "ROLE_CLIENTE"; 
    private final RolRepository rolRepository;

    public UsuarioFactory(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Usuario fromDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        // Usar nombre de rol en lugar de ID
        String rolNombre = dto.getIdRol() != null 
            ? rolRepository.findById(dto.getIdRol())
                .map(Rol::getNombre)
                .orElse(DEFAULT_ROL_NAME)
            : DEFAULT_ROL_NAME;

        Rol rol = rolRepository.findByNombre(rolNombre)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
            
        usuario.getRoles().add(rol);
        
        return usuario;
    }
}