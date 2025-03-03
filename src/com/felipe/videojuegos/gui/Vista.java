package com.felipe.videojuegos.gui;

import com.felipe.videojuegos.gui.base.Desarrollador;
import com.felipe.videojuegos.gui.base.Videojuego;
import com.felipe.videojuegos.gui.enums.Genero;
import com.felipe.videojuegos.gui.enums.TipoDesarrollador;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La clase Vista representa la interfaz gráfica principal de la aplicación de la tienda de videojuegos.
 * Proporciona componentes gráficos y métodos para gestionar las vistas de videojuegos, desarrolladores,
 * puntuaciones, usuarios y compras.
 * Además, contiene configuraciones iniciales para el menú y los diálogos de opciones.
 */
public class Vista extends JFrame {

    // Componentes principales
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private final static String TITULO_FRAME = "Lista Videojuegos";

    // Componentes de desarrolladores
    public JTextField tfDevNombre;
    public JTextField tfDevEmail;
    public JTextField tfDevExperiencia;
    public JComboBox cbDevTipoDesarrollo;
    public JTable tablaDesarrolladores;
    public JButton bDevAgregar;
    public JButton bDevModificar;
    public JButton bDevEliminar;
    public JButton bDevLimpiar;
    public JButton bDevEfectuarModificacion;
    public JButton bDevCancelarModificacion;
    public JTextField tfDevBuscar;

    // Componentes de videojuegos
    public JTextField tfJuegoTitulo;
    public JComboBox cbJuegoGenero;
    public JTextField tfJuegoPrecio;
    public JComboBox cbJuegoDesarrolladores;
    public JRadioButton rbJuegoPc;
    public JRadioButton rbJuegoPlaystation;
    public JRadioButton rbJuegoXbox;
    public JButton bJuegoAgregarDev;
    public JButton bJuegoEliminarDev;
    public JList listaDesarrolladoresDeJuego;
    public JTable tablaJuegos;
    public JButton bJuegoAgregar;
    public JButton bJuegoModificar;
    public JButton bJuegoEliminar;
    public DatePicker dpJuegoLanzamiento;
    public JButton bJuegoLimpiar;
    public JButton bJuegoEfectuarModificacion;
    public JButton bJuegoCancelarModificacion;
    public JTextField tfJuegoBuscar;

    // Componentes de puntuaciones
    public JComboBox cbRatingJuego;
    public JTextField tfRatingFuente;
    public JTextField tfRatingPuntuacion;
    public JTable tablaPuntuaciones;
    public JButton bRatingAgregar;
    public JButton bRatingModificar;
    public JButton bRatingEliminar;
    public DatePicker dpRatingFecha;
    public JButton bRatingLimpiar;
    public JButton bRatingEfectuarModificacion;
    public JButton bRatingCancelarModificacion;
    public JTextField tfRatingBuscar;

    // Componentes del menú
    JMenuItem itemConectar;
    JMenuItem itemSalir;

    JPopupMenu menuContextualDev;
    JPopupMenu menuContextualJuego;
    JMenuItem itemVerJuegos;
    JMenuItem itemVerDevs;

    // Modelos de tablas y listas
    DefaultTableModel dtmDesarrollador;
    DefaultTableModel dtmJuegos;
    DefaultTableModel dtmPuntuaciones;
    DefaultListModel<Desarrollador> dlmDevsDeJuego;
    DefaultListModel<Videojuego> dlmJuegosDeUnDev;

    /**
     * Constructor de la clase VistaListaEntidad. Inicializa la ventana y los modelos de lista.
     */
    public Vista() {
        super(TITULO_FRAME);
        initFrame();
        setMenu();
        setEnumComboBox();
        setTableModels();
        setListModels();
        visiblesBotonesLimpiar(false);
        visiblesBotonesRealizarModificacion(false);
        crearMenuContextual();
        agregarMenuATabla(tablaDesarrolladores, menuContextualDev);
        agregarMenuATabla(tablaJuegos, menuContextualJuego);

        rbJuegoPc.setSelected(true);
    }

    /**
     * Configura el marco principal de la aplicación.
     */
    private void initFrame() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        this.setSize(new Dimension(this.getWidth() + 20, this.getHeight() + 50));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Configura el menú principal de la aplicación.
     */
    private void setMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        itemConectar = new JMenuItem("Conectar");
        itemConectar.setActionCommand("Conectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");

        menu.add(itemConectar);
        menu.add(itemSalir);

        barra.add(menu);
        barra.add(Box.createHorizontalGlue());
        this.setJMenuBar(barra);
    }

    /**
     * Configura los valores iniciales de los ComboBox con enumeraciones.
     */
    private void setEnumComboBox() {
        cbJuegoGenero.addItem("Elegir género");
        for (Genero unGenero : Genero.values()) {
            cbJuegoGenero.addItem(unGenero.getNombre());
        }

        cbDevTipoDesarrollo.addItem("Elegir tipo de desarrollo");
        for (TipoDesarrollador unTipo : TipoDesarrollador.values()) {
            cbDevTipoDesarrollo.addItem(unTipo.getNombre());
        }
    }

    /**
     * Configura los modelos de las tablas.
     */
    private void setTableModels() {
        this.dtmJuegos = new DefaultTableModel();
        this.dtmJuegos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.dtmJuegos.setColumnIdentifiers(new String[] {"ID", "Título", "Género", "Precio", "Plataforma", "Lanzamiento"});
        this.tablaJuegos.setModel(dtmJuegos);
        this.tablaJuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dtmDesarrollador = new DefaultTableModel();
        this.dtmDesarrollador = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.dtmDesarrollador.setColumnIdentifiers(new String[] {"ID", "Nombre", "Email", "AñosExperiencia", "Tipo"});
        this.tablaDesarrolladores.setModel(dtmDesarrollador);
        this.tablaDesarrolladores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dtmPuntuaciones = new DefaultTableModel();
        this.dtmPuntuaciones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.dtmPuntuaciones.setColumnIdentifiers(new String[] {"ID", "Juego", "Fuente", "Nota", "Fecha"});
        this.tablaPuntuaciones.setModel(dtmPuntuaciones);
        this.tablaPuntuaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Configura los modelos de las listas.
     */
    private void setListModels() {
        this.dlmDevsDeJuego = new DefaultListModel<>();
        this.listaDesarrolladoresDeJuego.setModel(dlmDevsDeJuego);
        this.listaDesarrolladoresDeJuego.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Dar visibilidad a los botones usados para limpiar los campos de la Vista.
     * @param estado Puede ser true o false, refiriéndose a si quieren que se vea o no.
     */
    private void visiblesBotonesLimpiar(boolean estado) {
        bDevLimpiar.setVisible(estado);
        bJuegoLimpiar.setVisible(estado);
        bRatingLimpiar.setVisible(estado);
    }

    /**
     * Dar visibilidad a los botones usados para modificar las entidades de la Vista.
     * @param estado Puede ser true o false, refiriéndose a si quieren que se vea o no.
     */
    private void visiblesBotonesRealizarModificacion(boolean estado) {
        bDevEfectuarModificacion.setVisible(estado);
        bDevCancelarModificacion.setVisible(estado);
        bJuegoEfectuarModificacion.setVisible(estado);
        bJuegoCancelarModificacion.setVisible(estado);
        bRatingEfectuarModificacion.setVisible(estado);
        bRatingCancelarModificacion.setVisible(estado);
    }

    /**
     * Crea y configura los menús contextuales para el desarrollador y el usuario.
     * Este método genera dos menús contextuales, uno para el desarrollador que contiene
     * la opción "Ver Videojuegos" y otro para el usuario con la opción "Ver Tickets".
     * Los menús se configuran con los elementos correspondientes y se asignan las acciones
     * necesarias a los items del menú.
     */
    private void crearMenuContextual() {
        // Crear el menú contextual
        menuContextualDev = new JPopupMenu();
        itemVerJuegos = new JMenuItem("Ver Videojuegos");
        itemVerJuegos.setActionCommand("Ver Videojuegos");
        // Agregar opciones al menú
        menuContextualDev.add(itemVerJuegos);

        menuContextualJuego = new JPopupMenu();
        itemVerDevs = new JMenuItem("Ver Desarrolladores");
        itemVerDevs.setActionCommand("Ver Desarrolladores");
        menuContextualJuego.add(itemVerDevs);
    }

    /**
     * Agrega un menú contextual a una tabla, de modo que se muestre al hacer clic derecho
     * sobre cualquier celda de la tabla.
     * Este método configura un MouseListener en la tabla para detectar clics con el botón derecho
     * del ratón y mostrar el menú contextual en la ubicación del clic. Se asegura de que se
     * seleccione la fila correspondiente cuando se hace clic en la tabla y de que el menú
     * contextual se muestre correctamente.
     * @param tabla La tabla a la que se le agregará el menú contextual.
     * @param menu El menú contextual que se mostrará al hacer clic derecho sobre la tabla.
     */
    private void agregarMenuATabla(JTable tabla, JPopupMenu menu) {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMenu(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarMenu(e);
            }
            private void mostrarMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = tabla.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        tabla.setRowSelectionInterval(row, row);
                        menu.show(tabla, e.getX(), e.getY());
                    }
                }
            }
        });
    }
}