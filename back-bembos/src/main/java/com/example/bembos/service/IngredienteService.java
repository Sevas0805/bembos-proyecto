package com.example.bembos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bembos.dto.IngredienteDTO;
import com.example.bembos.exception.ResourceNotFoundException;
import com.example.bembos.model.Ingrediente;
import com.example.bembos.model.TipoIngrediente;
import com.example.bembos.repository.IngredienteRepository;
import com.example.bembos.repository.TipoIngredienteRepository;

@Service
public class IngredienteService {
    
    @Autowired
    private IngredienteRepository ingredienteRepository;
    
    @Autowired
    private TipoIngredienteRepository tipoIngredienteRepository;

    public List<IngredienteDTO> obtenerTodos() {
        return ingredienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<IngredienteDTO> obtenerPorTipo(Long tipoId) {
        TipoIngrediente tipo = tipoIngredienteRepository.findById(tipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de ingrediente no encontrado con id: " + tipoId));
        
        return ingredienteRepository.findByTipoIngrediente(tipo).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public IngredienteDTO obtenerPorId(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con id: " + id));
        return convertirADTO(ingrediente);
    }

    public IngredienteDTO crear(IngredienteDTO dto) {
        TipoIngrediente tipo = tipoIngredienteRepository.findById(dto.getTipoIngredienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de ingrediente no encontrado con id: " + dto.getTipoIngredienteId()));
        
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(dto.getNombre());
        ingrediente.setDescripcion(dto.getDescripcion());
        ingrediente.setPrecio(dto.getPrecio());
        ingrediente.setImagenUrl(dto.getImagenUrl());
        ingrediente.setTipoIngrediente(tipo);
        
        return convertirADTO(ingredienteRepository.save(ingrediente));
    }

    public IngredienteDTO actualizar(Long id, IngredienteDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con id: " + id));
        
        TipoIngrediente tipo = tipoIngredienteRepository.findById(dto.getTipoIngredienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de ingrediente no encontrado con id: " + dto.getTipoIngredienteId()));
        
        ingrediente.setNombre(dto.getNombre());
        ingrediente.setDescripcion(dto.getDescripcion());
        ingrediente.setPrecio(dto.getPrecio());
        ingrediente.setImagenUrl(dto.getImagenUrl());
        ingrediente.setTipoIngrediente(tipo);
        
        return convertirADTO(ingredienteRepository.save(ingrediente));
    }

    public void eliminar(Long id) {
        if (!ingredienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente no encontrado con id: " + id);
        }
        ingredienteRepository.deleteById(id);
    }

    private IngredienteDTO convertirADTO(Ingrediente ingrediente) {
        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(ingrediente.getId());
        dto.setNombre(ingrediente.getNombre());
        dto.setDescripcion(ingrediente.getDescripcion());
        dto.setPrecio(ingrediente.getPrecio());
        dto.setImagenUrl(ingrediente.getImagenUrl());
        dto.setTipoIngredienteId(ingrediente.getTipoIngrediente().getId());
        dto.setTipoIngredienteNombre(ingrediente.getTipoIngrediente().getNombre());
        return dto;
    }
}