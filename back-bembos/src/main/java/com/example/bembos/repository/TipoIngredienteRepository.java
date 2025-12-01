package com.example.bembos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bembos.model.TipoIngrediente;

@Repository
public interface TipoIngredienteRepository extends JpaRepository<TipoIngrediente, Long> {
    // Buscar por nombre
    TipoIngrediente findByNombre(String nombre);
}