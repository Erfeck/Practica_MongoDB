package com.felipe.videojuegos.gui.enums;

/**
 * La enumeración TipoDesarrollador representa los diferentes tipos de estudios de desarrollo de videojuegos.
 * Cada tipo describe la categoría del estudio con base en su tamaño y enfoque en el mercado.
 */
public enum TipoDesarrollador {

    ESTUDIO_AAA("Estudio AAA"),
    ESTUDIO_INDIE("Estudio Indie"),
    ESTUDIO_DE_NICHO("Estudio de Nicho");

    private String nombre; // El nombre del tipo de estudio

    /**
     * Constructor de la enumeración TipoDesarrollador.
     * @param nombre El nombre del tipo de desarrollador.
     */
    TipoDesarrollador(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del tipo de desarrollador de videojuegos.
     * @return El nombre del tipo de desarrollador.
     */
    public String getNombre() {
        return nombre;
    }
}
