package com.example.bembos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.bembos.dto.CrearHamburguesaDTO;
import com.example.bembos.dto.HamburguesaDTO;
import com.example.bembos.service.HamburguesaService;

@RestController
@RequestMapping("/api/hamburguesas")
@CrossOrigin(origins = "*")
public class HamburguesaController {

    @Autowired
    private HamburguesaService hamburguesaService;

    @GetMapping
    public ResponseEntity<List<HamburguesaDTO>> listarTodas() {
        return ResponseEntity.ok(hamburguesaService.obtenerTodas());
    }

    @GetMapping("/populares")
    public ResponseEntity<List<HamburguesaDTO>> listarPopulares() {
        return ResponseEntity.ok(hamburguesaService.obtenerMasPopulares());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HamburguesaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(hamburguesaService.obtenerPorCreador(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HamburguesaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hamburguesaService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('BURGERBUILDER')")
    public ResponseEntity<HamburguesaDTO> crear(@RequestBody CrearHamburguesaDTO dto) {
        HamburguesaDTO nuevaHamburguesa = hamburguesaService.crear(dto);
        return new ResponseEntity<>(nuevaHamburguesa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HamburguesaDTO> actualizar(
            @PathVariable Long id,
            @RequestBody CrearHamburguesaDTO dto) {
        return ResponseEntity.ok(hamburguesaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        hamburguesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}