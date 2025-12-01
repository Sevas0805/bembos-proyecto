package com.example.bembos.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bembos.model.Hamburguesa;
import com.example.bembos.model.Publicacion;
import com.example.bembos.model.Usuario;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    // Buscar por usuario
    List<Publicacion> findByUsuario(Usuario usuario);
    
    // Buscar por hamburguesa
    List<Publicacion> findByHamburguesa(Hamburguesa hamburguesa);
    
    // Buscar por título que contenga
    List<Publicacion> findByTituloContainingIgnoreCase(String titulo);
    
    // Obtener las publicaciones más votadas
    Page<Publicacion> findAllByOrderByVotosDesc(Pageable pageable);
    
    // Buscar publicaciones recientes
    Page<Publicacion> findAllByOrderByFechaCreacionDesc(Pageable pageable);
    
    // Buscar por usuario y ordenar por fecha
    List<Publicacion> findByUsuarioOrderByFechaCreacionDesc(Usuario usuario);
}