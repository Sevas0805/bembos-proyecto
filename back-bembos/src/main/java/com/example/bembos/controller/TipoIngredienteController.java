package com.example.bembos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bembos.dto.TipoIngredienteDTO;
import com.example.bembos.service.TipoIngredienteService;

@RestController
@RequestMapping("/api/tipos-ingrediente")
@CrossOrigin(origins = "*")
public class TipoIngredienteController {

    @Autowired
    private TipoIngredienteService tipoIngredienteService;

    @GetMapping
    public ResponseEntity<List<TipoIngredienteDTO>> listarTodos() {
        return ResponseEntity.ok(tipoIngredienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoIngredienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoIngredienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoIngredienteDTO> crear(@RequestBody TipoIngredienteDTO dto) {
        TipoIngredienteDTO nuevoTipo = tipoIngredienteService.crear(dto);
        return new ResponseEntity<>(nuevoTipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoIngredienteDTO> actualizar(
            @PathVariable Long id,
            @RequestBody TipoIngredienteDTO dto) {
        return ResponseEntity.ok(tipoIngredienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoIngredienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}