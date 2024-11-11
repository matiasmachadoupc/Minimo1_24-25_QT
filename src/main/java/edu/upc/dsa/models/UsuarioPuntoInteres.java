package edu.upc.dsa.models;

public class UsuarioPuntoInteres {
    private Usuario usuario;
    private PuntoInteres puntoInteres;

    public UsuarioPuntoInteres() {
    }

    public UsuarioPuntoInteres(Usuario usuario, PuntoInteres puntoInteres) {
        this.usuario = usuario;
        this.puntoInteres = puntoInteres;
    }

    // Getters and Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PuntoInteres getPuntoInteres() {
        return puntoInteres;
    }

    public void setPuntoInteres(PuntoInteres puntoInteres) {
        this.puntoInteres = puntoInteres;
    }
}