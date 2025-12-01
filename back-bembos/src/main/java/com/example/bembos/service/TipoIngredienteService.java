package com.example.bembos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bembos.dto.TipoIngredienteDTO;
import com.example.bembos.exception.ResourceNotFoundException;
import com.example.bembos.model.TipoIngrediente;
import com.example.bembos.repository.TipoIngredienteRepository;

@Service
public class TipoIngredienteService {
    
    @Autowired
    private TipoIngredienteRepository tipoIngredienteRepository;

    public List<TipoIngredienteDTO> obtenerTodos() {
        return tipoIngredienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public TipoIngredienteDTO obtenerPorId(Long id) {
        TipoIngrediente tipoIngrediente = tipoIngredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de ingrediente no encontrado con id: " + id));
        return convertirADTO(tipoIngrediente);
    }

    public TipoIngredienteDTO crear(TipoIngredienteDTO dto) {
        TipoIngrediente tipoIngrediente = new TipoIngrediente();
        tipoIngrediente.setNombre(dto.getNombre());
        tipoIngrediente.setDescripcion(dto.getDescripcion());
        
        return convertirADTO(tipoIngredienteRepository.save(tipoIngrediente));
    }

    public TipoIngredienteDTO actualizar(Long id, TipoIngredienteDTO dto) {
        TipoIngrediente tipoIngrediente = tipoIngredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de ingrediente no encontrado con id: " + id));
        
        tipoIngrediente.setNombre(dto.getNombre());
        tipoIngrediente.setDescripcion(dto.getDescripcion());
        
        return convertirADTO(tipoIngredienteRepository.save(tipoIngrediente));
    }

    public void eliminar(Long id) {
        if (!tipoIngredienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de ingrediente no encontrado con id: " + id);
        }
        tipoIngredienteRepository.deleteById(id);
    }

    private TipoIngredienteDTO convertirADTO(TipoIngrediente tipoIngrediente) {
        TipoIngredienteDTO dto = new TipoIngredienteDTO();
        dto.setId(tipoIngrediente.getId());
        dto.setNombre(tipoIngrediente.getNombre());
        dto.setDescripcion(tipoIngrediente.getDescripcion());
        return dto;
    }
}