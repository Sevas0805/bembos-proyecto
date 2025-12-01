package com.example.bembos.dto;

public class ClienteDTO {

    private Long id;
    private String telefono;
    private String direccion;
    private UsuarioDTO usuario;
    private RolDTO rol;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String telefono, UsuarioDTO usuario, RolDTO rol, String direccion) {
        this.id = id;
        this.telefono = telefono;
        this.usuario = usuario;
        this.rol = rol;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
