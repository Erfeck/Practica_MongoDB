package com.felipe.videojuegos.util;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase Util contiene métodos para mostrar diferentes tipos de alertas utilizando JOptionPane.
 * Esta clase proporciona métodos estáticos para mostrar alertas de error, advertencia e información.
 */
public class Util {

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     * @param mensaje el mensaje que se mostrará en el cuadro de diálogo.
     */
    public static void showErrorAlert(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de advertencia.
     * @param mensaje el mensaje que se mostrará en el cuadro de diálogo.
     */
    public static void showWarningAlert(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de información.
     * @param mensaje el mensaje que se mostrará en el cuadro de diálogo.
     */
    public static void showInfoAlert(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String formatearFecha(LocalDate fecha) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formateador.format(fecha);
    }

    public static LocalDate parsearFecha(String fecha){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fecha, formateador);
    }
}

