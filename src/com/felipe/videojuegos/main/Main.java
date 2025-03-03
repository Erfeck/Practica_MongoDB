package com.felipe.videojuegos.main;

import com.felipe.videojuegos.gui.Controlador;
import com.felipe.videojuegos.gui.Modelo;
import com.felipe.videojuegos.gui.Vista;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();

        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
    }
}