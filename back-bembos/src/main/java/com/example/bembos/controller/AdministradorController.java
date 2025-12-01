package com.example.bembos.controller;

import com.example.bembos.dto.AdministradorDTO;
import com.example.bembos.model.Administrador;
import com.example.bembos.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    //Crear un nuevo administrador
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> addAdministrador(@RequestBody AdministradorDTO dto) {
        try {
            Administrador nuevoAdmin = administradorService.addAdministrador(dto);
            return ResponseEntity.ok(nuevoAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Obtener todos los administradores 
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Administrador>> getAllAdministradores() {
        List<Administrador> administradores = administradorService.getAllAdministradores();
        return ResponseEntity.ok(administradores);
    }

    // Obtener un administrador por ID 
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> getAdministradorById(@PathVariable Long id) {
        try {
            Administrador admin = administradorService.getAdministradorById(id);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Obtener un administrador por id de usuario
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CLIENTE')")
    public ResponseEntity<?> getAdministradorByUsuarioId(@PathVariable Long usuarioId) {
        try {
            Administrador admin = administradorService.getAdministradorByUsuarioId(usuarioId);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //  Eliminar un administrador
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> deleteAdministrador(@PathVariable Long id) {
        try {
            administradorService.deleteAdministrador(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
