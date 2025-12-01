package com.example.bembos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bembos.dto.IngredienteDTO;
import com.example.bembos.service.IngredienteService;

@RestController
@RequestMapping("/api/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public ResponseEntity<List<IngredienteDTO>> listarTodos() {
        return ResponseEntity.ok(ingredienteService.obtenerTodos());
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<IngredienteDTO>> listarPorTipo(@PathVariable Long tipoId) {
        return ResponseEntity.ok(ingredienteService.obtenerPorTipo(tipoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingredienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<IngredienteDTO> crear(@RequestBody IngredienteDTO dto) {
        IngredienteDTO nuevoIngrediente = ingredienteService.crear(dto);
        return new ResponseEntity<>(nuevoIngrediente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteDTO> actualizar(
            @PathVariable Long id,
            @RequestBody IngredienteDTO dto) {
        return ResponseEntity.ok(ingredienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ingredienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}