package com.example.bembos.controller;

import com.example.bembos.dto.UsuarioDTO;
import com.example.bembos.model.Usuario;
import com.example.bembos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> registroUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            String jwt = usuarioService.addUsuario(usuarioDTO);
            return ResponseEntity.ok().body("Usuario registrado con éxito. Token: " + jwt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Obtener todos los usuarios
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    // Obtener usuario por ID
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.getUsuarioById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar usuario
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE') or hasRole('BURGERBUILDER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario userDetails) {
        try {
            Usuario updated = usuarioService.updateUsuario(id, userDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar usuario por email 
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUsuarioByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(usuarioService.getUsuarioByEmail(email));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}