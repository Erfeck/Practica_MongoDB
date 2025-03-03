package com.felipe.videojuegos.gui.base;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.List;

public class Videojuego {
    private ObjectId objectId;
    private String titulo;
    private String genero;
    private double precio;
    private String plataforma;
    private LocalDate fechaLanzamiento;
    private List<Desarrollador> desarrolladores;

    public Videojuego() {
    }
    public Videojuego(String titulo, String genero, double precio, String plataforma, LocalDate fechaLanzamiento, List<Desarrollador> desarrolladores) {
        this.titulo = titulo;
        this.genero = genero;
        this.precio = precio;
        this.plataforma = plataforma;
        this.fechaLanzamiento = fechaLanzamiento;
        this.desarrolladores = desarrolladores;
    }

    public ObjectId getObjectId() {
        return objectId;
    }
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getPlataforma() {
        return plataforma;
    }
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }
    public List<Desarrollador> getDesarrolladores() {
        return desarrolladores;
    }
    public void setDesarrolladores(List<Desarrollador> desarrolladores) {
        this.desarrolladores = desarrolladores;
    }

    @Override
    public String toString() {
        return titulo + " (" + plataforma + ")";
    }
}