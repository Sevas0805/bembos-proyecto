package com.example.bembos.service;

import com.example.bembos.dto.UsuarioDTO;
import com.example.bembos.factory.UsuarioFactory;
import com.example.bembos.model.Usuario;
import com.example.bembos.repository.UsuarioRepository;
import com.example.bembos.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioFactory usuarioFactory;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear nuevo usuario
    public String addUsuario(UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Correo electrónico ya registrado");
        }

        Usuario usuario = usuarioFactory.fromDTO(dto);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        // Generar token JWT (usando nombre y lista de roles)
        return jwtUtil.generateToken(
                usuario.getEmail(),
                usuario.getRoles().stream().map(r -> r.getNombre()).toList(),
                usuario.getNombre(),
                usuario.getApellido()
                );
    }

    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Actualizar datos del usuario
    public Usuario updateUsuario(Long id, Usuario userDetails) {
        Usuario usuario = getUsuarioById(id);

        // Actualizar nombre
        if (userDetails.getNombre() != null) {
            usuario.setNombre(userDetails.getNombre());
        }

        // Actualizar email
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new RuntimeException("Correo electrónico ya registrado");
            }
            usuario.setEmail(userDetails.getEmail());
        }

        // Actualizar contraseña
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    // Buscar usuario por email
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}