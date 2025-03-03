package com.felipe.videojuegos.gui.base;

import org.bson.types.ObjectId;
import java.time.LocalDate;

public class Puntuacion {
    private ObjectId objectId;
    private Videojuego videojuego;
    private String fuente;
    private double nota;
    private LocalDate fechaPuntuacion;

    public Puntuacion() {
    }
    public Puntuacion(Videojuego videojuego, String fuente, double nota, LocalDate fechaPuntuacion) {
        this.videojuego = videojuego;
        this.fuente = fuente;
        this.nota = nota;
        this.fechaPuntuacion = fechaPuntuacion;
    }

    public ObjectId getObjectId() {
        return objectId;
    }
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    public Videojuego getVideojuego() {
        return videojuego;
    }
    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }
    public String getFuente() {
        return fuente;
    }
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public LocalDate getFechaPuntuacion() {
        return fechaPuntuacion;
    }
    public void setFechaPuntuacion(LocalDate fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
    }
}