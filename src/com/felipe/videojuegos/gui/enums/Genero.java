package com.felipe.videojuegos.gui.enums;

/**
 * La enumeración Género representa los distintos géneros de videojuegos disponibles en la tienda.
 * Cada género tiene un nombre que describe el tipo de juego que representa.
 */
public enum Genero {

    ACTION("Acción"),
    ADVENTURE("Aventura"),
    ROL("Rol"),
    SIMULATION("Simulación"),
    STRATEGY("Estrategia"),
    SPORTS("Deportes"),
    HORROR("Terror");

    private String nombre;  // Nombre del género de videojuego

    /**
     * Constructor de la enumeración Género.
     * @param nombre El nombre del género de videojuego.
     */
    Genero(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del género de videojuego.
     * @return El nombre del género.
     */
    public String getNombre() {
        return nombre;
    }
}
