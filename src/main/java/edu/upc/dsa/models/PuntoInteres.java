package edu.upc.dsa.models;

public class PuntoInteres {
    private int coordenadaVertical;
    private int coordenadaHorizontal;
    private ElementType tipo;

    public PuntoInteres() {
    }

    public PuntoInteres(int coordenadaVertical, int coordenadaHorizontal, ElementType tipo) {
        this.coordenadaVertical = coordenadaVertical;
        this.coordenadaHorizontal = coordenadaHorizontal;
        this.tipo = tipo;
    }

    // Getters and Setters
    public int getCoordenadaVertical() {
        return coordenadaVertical;
    }

    public void setCoordenadaVertical(int coordenadaVertical) {
        this.coordenadaVertical = coordenadaVertical;
    }

    public int getCoordenadaHorizontal() {
        return coordenadaHorizontal;
    }

    public void setCoordenadaHorizontal(int coordenadaHorizontal) {
        this.coordenadaHorizontal = coordenadaHorizontal;
    }

    public ElementType getTipo() {
        return tipo;
    }

    public void setTipo(ElementType tipo) {
        this.tipo = tipo;
    }
}