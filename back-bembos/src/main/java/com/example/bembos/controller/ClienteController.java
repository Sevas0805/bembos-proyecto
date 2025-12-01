package com.example.bembos.controller;

import com.example.bembos.dto.ClienteDTO;
import com.example.bembos.model.Cliente;
import com.example.bembos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> addCliente(@RequestBody ClienteDTO dto) {
        Cliente nuevoCliente = clienteService.addCliente(dto);
        return ResponseEntity.ok(nuevoCliente);
    }

    // Obtener todos los clientes
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    // Obtener cliente por ID
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    // Obtener cliente por ID de usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Cliente> getClienteByUsuarioId(@PathVariable Long usuarioId) {
        Cliente cliente = clienteService.getClienteByUsuarioId(usuarioId);
        return ResponseEntity.ok(cliente);
    }

    // Actualizar cliente
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(
            @PathVariable Long id,
            @RequestBody ClienteDTO dto) {
        Cliente clienteActualizado = clienteService.updateCliente(id, dto);
        return ResponseEntity.ok(clienteActualizado);
    }

    // Eliminar cliente
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
