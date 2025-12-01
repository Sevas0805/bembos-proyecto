package com.example.bembos.repository;

import com.example.bembos.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar cliente por usuario (útil para validar o mostrar sus datos)
    Optional<Cliente> findByUsuarioId(Long usuarioId);
}