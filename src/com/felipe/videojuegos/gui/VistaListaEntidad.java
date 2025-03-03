package com.felipe.videojuegos.gui;

import com.felipe.videojuegos.gui.base.Desarrollador;
import com.felipe.videojuegos.gui.base.Videojuego;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * La clase VistaListaEntidad es una ventana gráfica que muestra listas de entidades en la interfaz de usuario.
 * Puede mostrar una lista de objetos de tipo Videojuego o Ticket y su respectiva información.
 * Esta clase extiende de JFrame y se utiliza para mostrar la vista de las listas de las entidades en un panel.
 *
 */
public class VistaListaEntidad extends JFrame {
    private JPanel panel1;

    private final static String TITULO_FRAME = "Lista";
    public JList listaVistaEntidad;
    public JLabel labelVistaEntidad;

    DefaultListModel<Videojuego> dlmVistaEntidadJuego;
    DefaultListModel<Desarrollador> dlmVistaEntidadDesarrollador;

    /**
     * Constructor de la clase VistaListaEntidad. Inicializa la ventana y los modelos de lista.
     */
    public VistaListaEntidad() {
        super(TITULO_FRAME);
        initFrame();
        dlmVistaEntidadJuego = new DefaultListModel<>();
        dlmVistaEntidadDesarrollador = new DefaultListModel<>();
    }

    /**
     * Inicializa la ventana configurando el panel, el cierre y el tamaño de la ventana.
     */
    private void initFrame() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        this.setSize(new Dimension(this.getWidth() + 20, this.getHeight() + 50));
        this.setLocationRelativeTo(null);
    }

    /**
     * Prepara la vista para mostrar una lista de objetos Videojuego.
     * @param nombreVista El nombre de la vista que se mostrará en la etiqueta.
     * @param listaJuegos La lista de objetos Videojuego a mostrar en la lista.
     */
    public void prepararVistaJuegosDeUnDesarrollador(String nombreVista, List<Videojuego> listaJuegos) {
        listaVistaEntidad.setModel(dlmVistaEntidadJuego);
        dlmVistaEntidadJuego.clear();
        labelVistaEntidad.setText(nombreVista);
        for (Videojuego unJuego : listaJuegos) {
            dlmVistaEntidadJuego.addElement(unJuego);
        }
        this.setVisible(true);
    }

    /**
     * Prepara la vista para mostrar una lista de objetos Desarrolladores.
     * @param nombreVista El nombre de la vista que se mostrará en la etiqueta.
     * @param listaDevs La lista de objetos Videojuego a mostrar en la lista.
     */
    public void prepararVistaDevsDeUnJuego(String nombreVista, List<Desarrollador> listaDevs) {
        listaVistaEntidad.setModel(dlmVistaEntidadDesarrollador);
        dlmVistaEntidadDesarrollador.clear();
        labelVistaEntidad.setText(nombreVista);
        for (Desarrollador unDev: listaDevs) {
            dlmVistaEntidadDesarrollador.addElement(unDev);
        }
        this.setVisible(true);
    }
}