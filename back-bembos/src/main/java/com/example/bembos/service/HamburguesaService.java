package com.example.bembos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bembos.dto.CrearHamburguesaDTO;
import com.example.bembos.dto.HamburguesaDTO;
import com.example.bembos.dto.IngredienteDTO;
import com.example.bembos.exception.ResourceNotFoundException;
import com.example.bembos.model.Hamburguesa;
import com.example.bembos.model.Ingrediente;
import com.example.bembos.model.Usuario;
import com.example.bembos.repository.HamburguesaRepository;
import com.example.bembos.repository.IngredienteRepository;
import com.example.bembos.repository.UsuarioRepository;

@Service
public class HamburguesaService {
    
    @Autowired
    private HamburguesaRepository hamburguesaRepository;
    
    @Autowired
    private IngredienteRepository ingredienteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private IngredienteService ingredienteService;

    public List<HamburguesaDTO> obtenerTodas() {
        return hamburguesaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public HamburguesaDTO obtenerPorId(Long id) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hamburguesa no encontrada con id: " + id));
        return convertirADTO(hamburguesa);
    }

    public List<HamburguesaDTO> obtenerPorCreador(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        
        return hamburguesaRepository.findByCreador(usuario).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<HamburguesaDTO> obtenerMasPopulares() {
        return hamburguesaRepository.findMostPopular().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public HamburguesaDTO crear(CrearHamburguesaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        
        List<Ingrediente> ingredientes = dto.getIngredientesIds().stream()
                .map(ingredienteId -> ingredienteRepository.findById(ingredienteId)
                        .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con id: " + ingredienteId)))
                .collect(Collectors.toList());
        
        Hamburguesa hamburguesa = new Hamburguesa();
        hamburguesa.setNombre(dto.getNombre());
        hamburguesa.setCreador(usuario);
        hamburguesa.setIngredientes(ingredientes);
        
        // Calcular precio total
        double precioTotal = ingredientes.stream()
                .mapToDouble(Ingrediente::getPrecio)
                .sum();
        hamburguesa.setPrecioTotal(precioTotal);
        
        return convertirADTO(hamburguesaRepository.save(hamburguesa));
    }

    public HamburguesaDTO actualizar(Long id, CrearHamburguesaDTO dto) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hamburguesa no encontrada con id: " + id));
        
        List<Ingrediente> ingredientes = dto.getIngredientesIds().stream()
                .map(ingredienteId -> ingredienteRepository.findById(ingredienteId)
                        .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con id: " + ingredienteId)))
                .collect(Collectors.toList());
        
        hamburguesa.setNombre(dto.getNombre());
        hamburguesa.setIngredientes(ingredientes);
        
        // Recalcular precio total
        double precioTotal = ingredientes.stream()
                .mapToDouble(Ingrediente::getPrecio)
                .sum();
        hamburguesa.setPrecioTotal(precioTotal);
        
        return convertirADTO(hamburguesaRepository.save(hamburguesa));
    }

    public void eliminar(Long id) {
        if (!hamburguesaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hamburguesa no encontrada con id: " + id);
        }
        hamburguesaRepository.deleteById(id);
    }

    private HamburguesaDTO convertirADTO(Hamburguesa hamburguesa) {
        HamburguesaDTO dto = new HamburguesaDTO();
        dto.setId(hamburguesa.getId());
        dto.setNombre(hamburguesa.getNombre());
        dto.setPrecioTotal(hamburguesa.getPrecioTotal());
        dto.setImagenUrl(hamburguesa.getImagenUrl());
        dto.setCreadorId(hamburguesa.getCreador().getId());
        dto.setCreadorNombre(hamburguesa.getCreador().getNombre());
        
        List<IngredienteDTO> ingredientesDTO = hamburguesa.getIngredientes().stream()
                .map(ingrediente -> ingredienteService.obtenerPorId(ingrediente.getId()))   
                .collect(Collectors.toList());
        dto.setIngredientes(ingredientesDTO);
        
        return dto;
    }
}