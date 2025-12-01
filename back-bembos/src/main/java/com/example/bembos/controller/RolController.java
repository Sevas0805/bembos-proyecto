package com.example.bembos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.bembos.model.Rol;
import com.example.bembos.service.RolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<?> createRol(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.createRol(rol);
            return ResponseEntity.ok(nuevoRol);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
