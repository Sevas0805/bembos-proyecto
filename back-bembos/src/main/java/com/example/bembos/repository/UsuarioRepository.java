package com.example.bembos.repository;

import com.example.bembos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca un usuario por su email
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}