package com.example.bembos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bembos.model.Ingrediente;
import com.example.bembos.model.TipoIngrediente;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    // Buscar por tipo de ingrediente
    List<Ingrediente> findByTipoIngrediente(TipoIngrediente tipoIngrediente);
    
    // Buscar por nombre que contenga (ignorando mayúsculas/minúsculas)
    List<Ingrediente> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar por rango de precios
    List<Ingrediente> findByPrecioBetween(Double precioMin, Double precioMax);
}