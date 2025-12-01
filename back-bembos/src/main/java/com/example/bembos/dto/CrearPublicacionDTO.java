package com.example.bembos.dto;

public class CrearPublicacionDTO {
    private String titulo;
    private String descripcion;
    private Long hamburguesaId;
    private Long usuarioId;

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getHamburguesaId() {
        return hamburguesaId;
    }

    public void setHamburguesaId(Long hamburguesaId) {
        this.hamburguesaId = hamburguesaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}