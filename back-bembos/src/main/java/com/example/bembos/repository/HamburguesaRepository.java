package com.example.bembos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.bembos.model.Hamburguesa;
import com.example.bembos.model.Usuario;

@Repository
public interface HamburguesaRepository extends JpaRepository<Hamburguesa, Long> {
    // Buscar por creador
    List<Hamburguesa> findByCreador(Usuario creador);
    
    // Buscar por nombre que contenga
    List<Hamburguesa> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar por rango de precio
    List<Hamburguesa> findByPrecioTotalBetween(Double precioMin, Double precioMax);
    
    // Buscar hamburguesas que contengan un ingrediente específico
    @Query("SELECT DISTINCT h FROM Hamburguesa h JOIN h.ingredientes i WHERE i.id = ?1")
    List<Hamburguesa> findByIngredienteId(Long ingredienteId);
    
    // Buscar las hamburguesas más populares (basado en publicaciones y votos)
    @Query("SELECT h FROM Hamburguesa h LEFT JOIN Publicacion p ON p.hamburguesa = h GROUP BY h ORDER BY COUNT(p) DESC, SUM(COALESCE(p.votos, 0)) DESC")
    List<Hamburguesa> findMostPopular();
}