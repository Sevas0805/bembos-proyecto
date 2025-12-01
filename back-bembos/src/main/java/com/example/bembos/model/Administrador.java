package com.example.bembos.model;
import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    public void setId(Long id) {
        this.id = id;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public Rol getRol() {
        return rol;
    }
}
