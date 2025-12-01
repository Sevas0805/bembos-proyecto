package com.example.bembos.factory;

import com.example.bembos.dto.ClienteDTO;
import com.example.bembos.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteFactory {

    public Cliente fromDTO(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        if (dto.getId() != null) {
            cliente.setId(dto.getId());
        }
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        return cliente;
    }

}
