package com.example.bembos.factory;

import com.example.bembos.model.Administrador;
import com.example.bembos.dto.AdministradorDTO;
import com.example.bembos.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AdministradorFactory {

    public Administrador fromDTO(AdministradorDTO dto, Usuario usuario) {
        Administrador administrador = new Administrador();
        administrador.setId(dto.getId());
        administrador.setUsuario(usuario);
        return administrador;
    }

}
