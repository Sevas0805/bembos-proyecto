package com.example.bembos.factory;

import com.example.bembos.model.Rol;
import com.example.bembos.dto.RolDTO;
import org.springframework.stereotype.Component;

@Component
public class RolFactory {

    public RolDTO toDTO(Rol rol) {
        if (rol == null) {
            return null;
        }
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rol.getId());
        rolDTO.setNombre(rol.getNombre());
        return rolDTO;
    }

    public Rol toEntity(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setId(rolDTO.getId());
        rol.setNombre(rolDTO.getNombre());
        return rol;
    }

}
