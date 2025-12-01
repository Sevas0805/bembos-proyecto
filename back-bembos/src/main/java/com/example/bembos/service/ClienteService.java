package com.example.bembos.service;

import com.example.bembos.dto.ClienteDTO;
import com.example.bembos.factory.ClienteFactory;
import com.example.bembos.model.Cliente;
import com.example.bembos.model.Rol;
import com.example.bembos.model.Usuario;
import com.example.bembos.repository.ClienteRepository;
import com.example.bembos.repository.RolRepository;
import com.example.bembos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ClienteFactory clienteFactory;

    // Crear un nuevo cliente
    public Cliente addCliente(ClienteDTO dto) {
        Cliente cliente = clienteFactory.fromDTO(dto);

        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener rol CLIENTE por defecto
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        // Si tiene teléfono y dirección, asignar rol BURGERBUILDER
        if (dto.getTelefono() != null && !dto.getTelefono().isEmpty() &&
            dto.getDireccion() != null && !dto.getDireccion().isEmpty()) {
            
            Rol rolBurgerBuilder = rolRepository.findByNombre("BURGERBUILDER")
                    .orElseThrow(() -> new RuntimeException("Rol BURGERBUILDER no encontrado"));
            
            usuario.getRoles().add(rolBurgerBuilder);
            usuarioRepository.save(usuario);
        }

        cliente.setUsuario(usuario);
        cliente.setRol(rolCliente);

        return clienteRepository.save(cliente);
    }

    // Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Buscar cliente por ID
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    // Buscar cliente por ID de usuario
    public Cliente getClienteByUsuarioId(Long usuarioId) {
        return clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para este usuario"));
    }

    // Actualizar datos del cliente
    public Cliente updateCliente(Long id, ClienteDTO dto) {
        Cliente cliente = getClienteById(id);

        if (dto.getTelefono() != null) {
            cliente.setTelefono(dto.getTelefono());
        }
        return clienteRepository.save(cliente);
    }

    // Eliminar cliente
    public void deleteCliente(Long id) {
        Cliente cliente = getClienteById(id);
        clienteRepository.delete(cliente);
    }
}
