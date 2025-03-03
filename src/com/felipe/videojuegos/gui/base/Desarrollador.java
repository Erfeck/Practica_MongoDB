package com.felipe.videojuegos.gui.base;

import org.bson.types.ObjectId;
import java.util.List;

public class Desarrollador {
    private ObjectId objectId;
    private String nombre;
    private String email;
    private int experiencia;
    private String tipoDesarrollo;
    private List<Videojuego> videojuegos;

    public Desarrollador() {}
    public Desarrollador(String nombre, String email, int experiencia, String tipoDesarrollo, List<Videojuego> videojuegos) {
        this.nombre = nombre;
        this.email = email;
        this.experiencia = experiencia;
        this.tipoDesarrollo = tipoDesarrollo;
        this.videojuegos = videojuegos;
    }

    public ObjectId getObjectId() {
        return objectId;
    }
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getExperiencia() {
        return experiencia;
    }
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }
    public String getTipoDesarrollo() {
        return tipoDesarrollo;
    }
    public void setTipoDesarrollo(String tipoDesarrollo) {
        this.tipoDesarrollo = tipoDesarrollo;
    }
    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }
    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    @Override
    public String toString() {
        return nombre;
    }
}