package com.example.bembos.service;

import com.example.bembos.dto.AdministradorDTO;
import com.example.bembos.factory.AdministradorFactory;
import com.example.bembos.model.Administrador;
import com.example.bembos.model.Rol;
import com.example.bembos.model.Usuario;
import com.example.bembos.repository.AdministradorRepository;
import com.example.bembos.repository.RolRepository;
import com.example.bembos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AdministradorFactory administradorFactory;

    // Crear nuevo administrador
    public Administrador addAdministrador(AdministradorDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Administrador administrador = administradorFactory.fromDTO(dto, usuario);
        administrador.setRol(rol);

        return administradorRepository.save(administrador);
    }

    // Obtener todos los administradores
    public List<Administrador> getAllAdministradores() {
        return administradorRepository.findAll();
    }

    // Buscar administrador por ID
    public Administrador getAdministradorById(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }

    // Buscar administrador por ID de usuario
    public Administrador getAdministradorByUsuarioId(Long usuarioId) {
        return administradorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado para este usuario"));
    }

    // Eliminar administrador
    public void deleteAdministrador(Long id) {
        Administrador administrador = getAdministradorById(id);
        administradorRepository.delete(administrador);
    }
}
