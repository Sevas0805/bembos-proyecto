package com.example.bembos.dto;

public class IngredienteDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private Long tipoIngredienteId;
    private String tipoIngredienteNombre;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Long getTipoIngredienteId() {
        return tipoIngredienteId;
    }

    public void setTipoIngredienteId(Long tipoIngredienteId) {
        this.tipoIngredienteId = tipoIngredienteId;
    }

    public String getTipoIngredienteNombre() {
        return tipoIngredienteNombre;
    }

    public void setTipoIngredienteNombre(String tipoIngredienteNombre) {
        this.tipoIngredienteNombre = tipoIngredienteNombre;
    }
}