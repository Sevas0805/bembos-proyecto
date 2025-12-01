package com.example.bembos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.bembos.dto.CrearPublicacionDTO;
import com.example.bembos.dto.PublicacionDTO;
import com.example.bembos.exception.ResourceNotFoundException;
import com.example.bembos.model.Hamburguesa;
import com.example.bembos.model.Publicacion;
import com.example.bembos.model.Usuario;
import com.example.bembos.repository.HamburguesaRepository;
import com.example.bembos.repository.PublicacionRepository;
import com.example.bembos.repository.UsuarioRepository;

@Service
public class PublicacionService {
    
    @Autowired
    private PublicacionRepository publicacionRepository;
    
    @Autowired
    private HamburguesaRepository hamburguesaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private HamburguesaService hamburguesaService;

    public List<PublicacionDTO> obtenerTodas() {
        return publicacionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<PublicacionDTO> obtenerMasVotadas(int cantidad) {
        return publicacionRepository.findAllByOrderByVotosDesc(PageRequest.of(0, cantidad))
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<PublicacionDTO> obtenerRecientes(int cantidad) {
        return publicacionRepository.findAllByOrderByFechaCreacionDesc(PageRequest.of(0, cantidad))
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PublicacionDTO obtenerPorId(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con id: " + id));
        return convertirADTO(publicacion);
    }

    public PublicacionDTO crear(CrearPublicacionDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        
        Hamburguesa hamburguesa = hamburguesaRepository.findById(dto.getHamburguesaId())
                .orElseThrow(() -> new ResourceNotFoundException("Hamburguesa no encontrada con id: " + dto.getHamburguesaId()));
        
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setFechaCreacion(LocalDateTime.now());
        publicacion.setVotos(0);
        publicacion.setUsuario(usuario);
        publicacion.setHamburguesa(hamburguesa);
        
        return convertirADTO(publicacionRepository.save(publicacion));
    }

    public PublicacionDTO actualizarVotos(Long id, boolean incrementar) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con id: " + id));
        
        if (incrementar) {
            publicacion.setVotos(publicacion.getVotos() + 1);
        } else {
            publicacion.setVotos(publicacion.getVotos() - 1);
        }
        
        return convertirADTO(publicacionRepository.save(publicacion));
    }

    public void eliminar(Long id) {
        if (!publicacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Publicación no encontrada con id: " + id);
        }
        publicacionRepository.deleteById(id);
    }

    private PublicacionDTO convertirADTO(Publicacion publicacion) {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(publicacion.getId());
        dto.setTitulo(publicacion.getTitulo());
        dto.setDescripcion(publicacion.getDescripcion());
        dto.setFechaCreacion(publicacion.getFechaCreacion());
        dto.setVotos(publicacion.getVotos());
        dto.setUsuarioId(publicacion.getUsuario().getId());
        dto.setUsuarioNombre(publicacion.getUsuario().getNombre());
        dto.setHamburguesa(hamburguesaService.obtenerPorId(publicacion.getHamburguesa().getId()));
        
        return dto;
    }
}