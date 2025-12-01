package com.example.bembos.dto;

import java.util.List;

public class CrearHamburguesaDTO {
    private String nombre;
    private List<Long> ingredientesIds;
    private Long usuarioId;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Long> getIngredientesIds() {
        return ingredientesIds;
    }

    public void setIngredientesIds(List<Long> ingredientesIds) {
        this.ingredientesIds = ingredientesIds;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}