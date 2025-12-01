package com.example.bembos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bembos.dto.CrearPublicacionDTO;
import com.example.bembos.dto.PublicacionDTO;
import com.example.bembos.service.PublicacionService;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "*")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<PublicacionDTO>> listarTodas() {
        return ResponseEntity.ok(publicacionService.obtenerTodas());
    }

    @GetMapping("/populares")
    public ResponseEntity<List<PublicacionDTO>> listarMasVotadas(
            @RequestParam(defaultValue = "10") int cantidad) {
        return ResponseEntity.ok(publicacionService.obtenerMasVotadas(cantidad));
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<PublicacionDTO>> listarRecientes(
            @RequestParam(defaultValue = "10") int cantidad) {
        return ResponseEntity.ok(publicacionService.obtenerRecientes(cantidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(publicacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crear(@RequestBody CrearPublicacionDTO dto) {
        PublicacionDTO nuevaPublicacion = publicacionService.crear(dto);
        return new ResponseEntity<>(nuevaPublicacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/votar")
    public ResponseEntity<PublicacionDTO> votar(
            @PathVariable Long id,
            @RequestParam boolean incrementar) {
        return ResponseEntity.ok(publicacionService.actualizarVotos(id, incrementar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}