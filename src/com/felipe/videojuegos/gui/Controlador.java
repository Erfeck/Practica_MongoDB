package com.felipe.videojuegos.gui;

import com.felipe.videojuegos.gui.base.Desarrollador;
import com.felipe.videojuegos.gui.base.Puntuacion;
import com.felipe.videojuegos.gui.base.Videojuego;
import com.felipe.videojuegos.util.Util;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que maneja la lógica de interacción entre la vista y el modelo.
 * Actúa como intermediario para gestionar las acciones del usuario y
 * actualizar la vista en consecuencia. Además, gestiona las operaciones
 * de conexión, cierre de ventana y la manipulación de las entidades.
 */
public class Controlador implements ActionListener, KeyListener, ListSelectionListener {

    private Modelo modelo;
    private Vista vista;
    private VistaListaEntidad vistaListaEntidad;

    /**
     * Constructor del controlador, que establece la conexión entre el modelo y la vista,
     * y agrega los listeners necesarios para las acciones del usuario.
     * @param modelo El modelo que maneja la lógica de datos.
     * @param vista La vista que interactúa con el usuario.
     */
    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;

        addActionListeners(this);
        addKeyListeners(this);
        addWindowClosingListener();
        addListListeners(this);
    }

    /**
     * Añade los listeners para las acciones del usuario en los componentes de la vista.
     * Esto incluye los botones y los ítems del menú.
     * @param listener El listener de acción que se aplica a los componentes.
     */
    private void addActionListeners(ActionListener listener) {
        //Items Menu
        vista.itemConectar.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);

        //Items Popup
        vista.itemVerJuegos.addActionListener(listener);
        vista.itemVerDevs.addActionListener(listener);

        //Atributos Desarrollador
        vista.bDevAgregar.addActionListener(listener);
        vista.bDevModificar.addActionListener(listener);
        vista.bDevEliminar.addActionListener(listener);
        vista.bDevLimpiar.addActionListener(listener);
        vista.bDevEfectuarModificacion.addActionListener(listener);
        vista.bDevCancelarModificacion.addActionListener(listener);

        //Atributos Videojuego
        vista.bJuegoAgregar.addActionListener(listener);
        vista.bJuegoModificar.addActionListener(listener);
        vista.bJuegoEliminar.addActionListener(listener);
        vista.bJuegoLimpiar.addActionListener(listener);
        vista.bJuegoAgregarDev.addActionListener(listener);
        vista.bJuegoEliminarDev.addActionListener(listener);
        vista.bJuegoEfectuarModificacion.addActionListener(listener);
        vista.bJuegoCancelarModificacion.addActionListener(listener);

        //Atributos Puntuación
        vista.bRatingAgregar.addActionListener(listener);
        vista.bRatingModificar.addActionListener(listener);
        vista.bRatingEliminar.addActionListener(listener);
        vista.bRatingLimpiar.addActionListener(listener);
        vista.bRatingEfectuarModificacion.addActionListener(listener);
        vista.bRatingCancelarModificacion.addActionListener(listener);
    }

    /**
     * Añade los listeners para las acciones de teclado del usuario en los componentes de la vista.
     * Esto incluye los textos para buscar información detalla de las tablas.
     * @param listener El listener de acción que se aplica a los componentes.
     */
    private void addKeyListeners(KeyListener listener) {
        vista.tfDevBuscar.addKeyListener(listener);
        vista.tfJuegoBuscar.addKeyListener(listener);
        vista.tfRatingBuscar.addKeyListener(listener);
    }

    /**
     * Añade un listener para gestionar el evento de cierre de ventana.
     * Muestra una ventana de confirmación antes de cerrar la aplicación.
     * Si el usuario confirma, la aplicación se cierra.
     */
    private void addWindowClosingListener() {
        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcionParaSalir = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que quieres salir?",
                        "Salir App",
                        JOptionPane.YES_NO_OPTION);

                if (opcionParaSalir == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Añade un listener de selección a varias tablas en la vista.
     * Este listener se activa cuando el usuario selecciona una fila en alguna de las tablas
     * (Desarrolladores, Juegos, Puntuaciones, Usuarios, Compras).
     * @param listener El listener de selección que será agregado a las tablas.
     */
    private void addListListeners(ListSelectionListener listener) {
        vista.tablaDesarrolladores.getSelectionModel().addListSelectionListener(listener);
        vista.tablaJuegos.getSelectionModel().addListSelectionListener(listener);
        vista.tablaPuntuaciones.getSelectionModel().addListSelectionListener(listener);
    }

    /**
     * Método que maneja los eventos de acción generados por los componentes de la vista.
     * Este método se invoca cuando el usuario realiza una acción, como hacer clic en un botón
     * o seleccionar un ítem del menú.
     * El comportamiento específico de cada acción depende del comando asociado con el componente.
     * @param e El evento de acción que contiene información sobre el origen de la acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Conectar":
                modelo.setValues();
                modelo.conectarMongo();
                listarTodo();
                break;
            case "Salir":
                System.exit(0);
                break;
            case "Ver Videojuegos":
                if (vistaListaEntidad == null) {
                    vistaListaEntidad = new VistaListaEntidad();
                }
                int filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                String textoIdDev = String.valueOf(vista.tablaDesarrolladores.getValueAt(filaDesarrollador, 0));
                ObjectId objectIdDev = new ObjectId(textoIdDev);
                vistaListaEntidad.prepararVistaJuegosDeUnDesarrollador(
                        "Lista Videojuegos",
                        modelo.getJuegosDeUnDev(objectIdDev));
                break;
            case "Ver Desarrolladores":
                if (vistaListaEntidad == null) {
                    vistaListaEntidad = new VistaListaEntidad();
                }
                int filaJuego = vista.tablaJuegos.getSelectedRow();
                vistaListaEntidad.prepararVistaDevsDeUnJuego(
                        "Lista Desarrolladores",
                        modelo.getJuegos().get(filaJuego).getDesarrolladores());
                break;
            case "agregarDesarrollador":
                Desarrollador nuevoDesarrollador = new Desarrollador();
                nuevoDesarrollador.setNombre(vista.tfDevNombre.getText());
                nuevoDesarrollador.setEmail(vista.tfDevEmail.getText());
                nuevoDesarrollador.setExperiencia(Integer.parseInt(vista.tfDevExperiencia.getText()));
                nuevoDesarrollador.setTipoDesarrollo(String.valueOf(vista.cbDevTipoDesarrollo.getSelectedItem()));
                nuevoDesarrollador.setVideojuegos(null);
                modelo.guardarDev(nuevoDesarrollador);
                limpiarCamposDesarrollador();
                listarDesarrolladores(modelo.getDevs());
                break;
            case "agregarJuego":
                Videojuego nuevoVideojuego = new Videojuego();
                nuevoVideojuego.setTitulo(vista.tfJuegoTitulo.getText());
                nuevoVideojuego.setGenero(String.valueOf(vista.cbJuegoGenero.getSelectedItem()));
                nuevoVideojuego.setPrecio(Double.parseDouble(vista.tfJuegoPrecio.getText()));
                nuevoVideojuego.setPlataforma(elegirPlataforma());
                nuevoVideojuego.setFechaLanzamiento(Date.valueOf(vista.dpJuegoLanzamiento.getDate()).toLocalDate());
                nuevoVideojuego.setDesarrolladores(agregarDevsAJuego());
                modelo.guardarJuego(nuevoVideojuego);
                limpiarCamposVideojuego();
                listarVideojuegos(modelo.getJuegos());
                break;
            case "agregarPuntuacion":
                Puntuacion nuevaPuntuacion = new Puntuacion();
                nuevaPuntuacion.setVideojuego((Videojuego) vista.cbRatingJuego.getSelectedItem());
                nuevaPuntuacion.setFuente(vista.tfRatingFuente.getText());
                nuevaPuntuacion.setNota(Double.parseDouble(vista.tfRatingPuntuacion.getText()));
                nuevaPuntuacion.setFechaPuntuacion(Date.valueOf(String.valueOf(vista.dpRatingFecha.getDate())).toLocalDate());
                modelo.guardarPuntuacion(nuevaPuntuacion);
                limpiarCamposPuntuacion();
                listarPuntuaciones(modelo.getRatings());
                break;
            case "modificarDesarrollador":
                filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                if (filaDesarrollador == -1) {
                    Util.showErrorAlert("Selecciona un desarrollador");
                    return;
                }
                bloquearParametrosDesarrollador(false);
                habilitarModoModificacionDev(true);
                break;
            case "modificarJuego":
                filaJuego = vista.tablaJuegos.getSelectedRow();
                if (filaJuego == -1) {
                    Util.showErrorAlert("Selecciona un juego");
                    return;
                }
                bloquearParametrosVideojuego(false);
                habilitarModoModificacionJuego(true);
                break;
            case "modificarPuntuacion":
                int filaRating = vista.tablaPuntuaciones.getSelectedRow();
                if (filaRating == -1) {
                    Util.showErrorAlert("Selecciona una puntuación");
                    return;
                }
                bloquearParametrosPuntuacion(false);
                habilitarModoModificacionPuntuacion(true);
                break;
            case "eliminarDesarrollador":
                filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                if (filaDesarrollador == -1) {
                    Util.showErrorAlert("Selecciona un desarrollador");
                    return;
                }
                textoIdDev = String.valueOf(vista.tablaDesarrolladores.getValueAt(filaDesarrollador, 0));
                objectIdDev = new ObjectId(textoIdDev);
                modelo.borrarDev(modelo.getDevById(objectIdDev));
                listarDesarrolladores(modelo.getDevs());
                break;
            case "eliminarJuego":
                filaJuego = vista.tablaJuegos.getSelectedRow();
                if (filaJuego == -1) {
                    Util.showErrorAlert("Selecciona un videojuego");
                    return;
                }
                String textoIdJuego = String.valueOf(vista.tablaJuegos.getValueAt(filaJuego, 0));
                ObjectId objectIdJuego = new ObjectId(textoIdJuego);
                modelo.borrarJuego(modelo.getJuegoById(objectIdJuego));
                listarVideojuegos(modelo.getJuegos());
                break;
            case "eliminarPuntuacion":
                int filaPuntuacion = vista.tablaPuntuaciones.getSelectedRow();
                if (filaPuntuacion == -1) {
                    Util.showErrorAlert("Selecciona una puntuación");
                    return;
                }
                String textoIdPuntuacion = String.valueOf(vista.tablaPuntuaciones.getValueAt(filaPuntuacion, 0));
                ObjectId objectIdPuntuacion = new ObjectId(textoIdPuntuacion);
                modelo.borrarPuntuacion(modelo.getRatingById(objectIdPuntuacion));
                listarPuntuaciones(modelo.getRatings());
                break;
            case "limpiarSeleccionDesarrollador":
                bloquearParametrosDesarrollador(false);
                limpiarCamposDesarrollador();
                vista.tablaDesarrolladores.clearSelection();
                vista.bDevLimpiar.setVisible(false);
                break;
            case "limpiarSeleccionJuego":
                bloquearParametrosVideojuego(false);
                limpiarCamposVideojuego();
                vista.tablaJuegos.clearSelection();
                vista.bJuegoLimpiar.setVisible(false);
                break;
            case "limpiarSeleccionPuntuacion":
                bloquearParametrosPuntuacion(false);
                limpiarCamposPuntuacion();
                vista.tablaPuntuaciones.clearSelection();
                vista.bRatingLimpiar.setVisible(false);
                break;
            case "efectuarModificacionDev":
                filaDesarrollador = vista.tablaDesarrolladores.getSelectedRow();
                objectIdDev = new ObjectId(String.valueOf(vista.tablaDesarrolladores.getValueAt(filaDesarrollador, 0)));
                Desarrollador otroDesarrollador = new Desarrollador();
                otroDesarrollador.setObjectId(objectIdDev);
                otroDesarrollador.setNombre(vista.tfDevNombre.getText());
                otroDesarrollador.setEmail(vista.tfDevEmail.getText());
                otroDesarrollador.setExperiencia(Integer.parseInt(vista.tfDevExperiencia.getText()));
                otroDesarrollador.setTipoDesarrollo(String.valueOf(vista.cbDevTipoDesarrollo.getSelectedItem()));
                modelo.modificarDev(otroDesarrollador);
                limpiarCamposDesarrollador();
                listarDesarrolladores(modelo.getDevs());
                listarVideojuegos(modelo.getJuegos());
                vista.bDevCancelarModificacion.doClick();
                break;
            case "efectuarModificacionJuego":
                filaJuego = vista.tablaJuegos.getSelectedRow();
                objectIdJuego = new ObjectId(String.valueOf(vista.tablaJuegos.getValueAt(filaJuego, 0))) ;
                Videojuego otroJuego = new Videojuego();
                otroJuego.setObjectId(objectIdJuego);
                otroJuego.setTitulo(vista.tfJuegoTitulo.getText());
                otroJuego.setGenero(String.valueOf(vista.cbJuegoGenero.getSelectedItem()));
                otroJuego.setPrecio(Double.parseDouble(vista.tfJuegoPrecio.getText()));
                otroJuego.setPlataforma(elegirPlataforma());
                otroJuego.setFechaLanzamiento(Date.valueOf(vista.dpJuegoLanzamiento.getDate()).toLocalDate());
                otroJuego.setDesarrolladores(agregarDevsAJuego());
                modelo.modificarJuego(otroJuego);
                limpiarCamposVideojuego();
                listarVideojuegos(modelo.getJuegos());
                listarPuntuaciones(modelo.getRatings());
                vista.bJuegoCancelarModificacion.doClick();
                break;
            case "efectuarModificaciónPuntuacion":
                filaPuntuacion = vista.tablaPuntuaciones.getSelectedRow();
                objectIdPuntuacion = new ObjectId(String.valueOf(vista.tablaPuntuaciones.getValueAt(filaPuntuacion, 0)));
                Puntuacion otraPuntuacion = new Puntuacion();
                otraPuntuacion.setObjectId(objectIdPuntuacion);
                otraPuntuacion.setVideojuego((Videojuego) vista.cbRatingJuego.getSelectedItem());
                otraPuntuacion.setFuente(vista.tfRatingFuente.getText());
                otraPuntuacion.setNota(Double.parseDouble(vista.tfRatingPuntuacion.getText()));
                otraPuntuacion.setFechaPuntuacion(Date.valueOf(String.valueOf(vista.dpRatingFecha.getDate())).toLocalDate());
                modelo.modificarPuntuacion(otraPuntuacion);
                limpiarCamposPuntuacion();
                listarPuntuaciones(modelo.getRatings());
                vista.bRatingCancelarModificacion.doClick();
                break;
            case "cancelarModificacionDev":
                habilitarModoModificacionDev(false);
                vista.bDevLimpiar.doClick();
                break;
            case "cancelarModificaciónJuego":
                habilitarModoModificacionJuego(false);
                vista.bJuegoLimpiar.doClick();
                break;
            case "cancelarModificaciónPuntuacion":
                habilitarModoModificacionPuntuacion(false);
                vista.bRatingLimpiar.doClick();
                break;
            case "agregarDevAUnJuego":
                if (vista.cbJuegoDesarrolladores.getSelectedItem() != "Elegir desarrollador") {
                    if (!vista.dlmDevsDeJuego.contains(vista.cbJuegoDesarrolladores.getSelectedItem())) {
                        Desarrollador desarrolladorSeleccionado = (Desarrollador) vista.cbJuegoDesarrolladores.getSelectedItem();
                        vista.dlmDevsDeJuego.addElement(desarrolladorSeleccionado);
                    } else {
                        Util.showErrorAlert("Ese desarrollador ya esta en la lista");
                    }
                } else {
                    Util.showErrorAlert("Debes seleccionar un desarrollador");
                }
                break;
            case "eliminarDevAUnJuego":
                int devDeUnJuegoSeleccionado = vista.listaDesarrolladoresDeJuego.getSelectedIndex();
                if (devDeUnJuegoSeleccionado == -1) {
                    Util.showErrorAlert("Selecciona un desarrollador de la lista");
                    return;
                }
                vista.dlmDevsDeJuego.remove(devDeUnJuegoSeleccionado);
                break;
        }
    }

    /**
     * Método que maneja los eventos de cambio en la selección de filas en las tablas de la vista.
     * Este método se invoca cuando el usuario selecciona o deselecciona una fila en las tablas.
     * El comportamiento de la acción depende de la tabla que haya generado el evento de selección.
     * @param e El evento de selección que contiene la información sobre el cambio de selección,
     *          como las filas seleccionadas o deseleccionadas.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (e.getSource() == vista.tablaDesarrolladores.getSelectionModel() &&
                    !vista.tablaDesarrolladores.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaDesarrolladores.getSelectedRow();

                vista.tfDevNombre.setText(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 1)));
                vista.tfDevEmail.setText(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 2)));
                vista.tfDevExperiencia.setText(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 3)));
                vista.cbDevTipoDesarrollo.setSelectedItem(String.valueOf(vista.dtmDesarrollador.getValueAt(fila, 4)));

                vista.bDevLimpiar.setVisible(true);
                bloquearParametrosDesarrollador(true);
            } else if (e.getSource() == vista.tablaJuegos.getSelectionModel() &&
                    !vista.tablaJuegos.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaJuegos.getSelectedRow();

                vista.tfJuegoTitulo.setText(String.valueOf(vista.dtmJuegos.getValueAt(fila, 1)));
                vista.cbJuegoGenero.setSelectedItem(String.valueOf(vista.dtmJuegos.getValueAt(fila, 2)));
                vista.tfJuegoPrecio.setText(String.valueOf(vista.dtmJuegos.getValueAt(fila, 3)));
                setPlataformaVideojuego(String.valueOf(vista.dtmJuegos.getValueAt(fila, 4)));
                vista.dpJuegoLanzamiento.setDate(Date.valueOf(String.valueOf(vista.dtmJuegos.getValueAt(fila,5))).toLocalDate());

                setDesarrolladoresDeUnJuego(fila);

                vista.bJuegoLimpiar.setVisible(true);
                bloquearParametrosVideojuego(true);
            } else if (e.getSource() == vista.tablaPuntuaciones.getSelectionModel() &&
                    !vista.tablaPuntuaciones.getSelectionModel().isSelectionEmpty()) {
                int fila = vista.tablaPuntuaciones.getSelectedRow();

                vista.cbRatingJuego.setSelectedIndex(elegirJuegoComboBox(fila));
                vista.tfRatingFuente.setText(String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 2)));
                vista.tfRatingPuntuacion.setText(String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 3)));
                vista.dpRatingFecha.setDate(Date.valueOf(String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 4))).toLocalDate());

                vista.bRatingLimpiar.setVisible(true);
                bloquearParametrosPuntuacion(true);
            }
        }
    }

    /**
     * Llama a todos los métodos de listado (desarrolladores, videojuegos, usuarios, puntuaciones y compras)
     * para actualizar la vista con los datos más recientes de la base de datos.
     */
    public void listarTodo() {
        listarDesarrolladores(modelo.getDevs());
        listarVideojuegos(modelo.getJuegos());
        listarPuntuaciones(modelo.getRatings());
    }

    /**
     * Lista todos los desarrolladores y los agrega a la vista.
     * Actualiza el combobox de desarrolladores y la tabla de desarrolladores en la vista.
     */
    public void listarDesarrolladores(List<Desarrollador> listaDevs) {
        vista.cbJuegoDesarrolladores.removeAllItems();
        vista.dtmDesarrollador.setRowCount(0);

        vista.cbJuegoDesarrolladores.addItem("Elegir desarrollador");
        for (Desarrollador unDesarrollador : listaDevs) {
            vista.dtmDesarrollador.addRow(new Object[] {
                    unDesarrollador.getObjectId(),
                    unDesarrollador.getNombre(),
                    unDesarrollador.getEmail(),
                    unDesarrollador.getExperiencia(),
                    unDesarrollador.getTipoDesarrollo()
            });
            vista.cbJuegoDesarrolladores.addItem(unDesarrollador);
        }
    }

    /**
     * Lista todos los videojuegos y los agrega a la vista.
     * Actualiza los combo boxes de videojuegos en las secciones de puntuación y compra.
     */
    public void listarVideojuegos(List<Videojuego> listaJuegos) {
        vista.cbRatingJuego.removeAllItems();
        vista.dtmJuegos.setRowCount(0);

        vista.cbRatingJuego.addItem("Elegir videojuego");
        for (Videojuego unJuego : listaJuegos) {
            vista.dtmJuegos.addRow(new Object[] {
                    unJuego.getObjectId(),
                    unJuego.getTitulo(),
                    unJuego.getGenero(),
                    unJuego.getPrecio(),
                    unJuego.getPlataforma(),
                    unJuego.getFechaLanzamiento()
            });
            vista.cbRatingJuego.addItem(unJuego);
        }
    }

    /**
     * Lista todas las puntuaciones de videojuegos y las agrega a la vista.
     * Actualiza la tabla de puntuaciones en la vista con los datos correspondientes.
     */
    public void listarPuntuaciones(List<Puntuacion> listaPuntuaciones) {
        vista.dtmPuntuaciones.setRowCount(0);

        for (Puntuacion unaPuntuacion : listaPuntuaciones) {
            vista.dtmPuntuaciones.addRow(new Object[] {
                    unaPuntuacion.getObjectId(),
                    unaPuntuacion.getVideojuego().getTitulo() + " (" + unaPuntuacion.getVideojuego().getPlataforma() + ")",
                    unaPuntuacion.getFuente(),
                    unaPuntuacion.getNota(),
                    unaPuntuacion.getFechaPuntuacion()
            });
        }
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con los desarrolladores.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosDesarrollador(boolean estado) {
        vista.tfDevNombre.setEnabled(!estado);
        vista.tfDevEmail.setEnabled(!estado);
        vista.tfDevExperiencia.setEnabled(!estado);
        vista.cbDevTipoDesarrollo.setEnabled(!estado);
        vista.bDevAgregar.setEnabled(!estado);
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con los videojuegos.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosVideojuego(boolean estado) {
        vista.tfJuegoTitulo.setEnabled(!estado);
        vista.cbJuegoGenero.setEnabled(!estado);
        vista.tfJuegoPrecio.setEnabled(!estado);
        vista.rbJuegoPc.setEnabled(!estado);
        vista.rbJuegoPlaystation.setEnabled(!estado);
        vista.rbJuegoXbox.setEnabled(!estado);
        vista.dpJuegoLanzamiento.setEnabled(!estado);
        vista.cbJuegoDesarrolladores.setEnabled(!estado);
        vista.bJuegoAgregarDev.setEnabled(!estado);
        vista.bJuegoEliminarDev.setEnabled(!estado);
        //vista.listaDesarrolladoresDeJuego.setEnabled(!estado);
        vista.bJuegoAgregar.setEnabled(!estado);
    }

    /**
     * Bloquea o desbloquea los campos de entrada y botones relacionados con las puntuaciones.
     * Si el parámetro 'estado' es true, se bloquean los campos; si es false, se desbloquean.
     * @param estado El estado que determina si los campos deben ser bloqueados (true) o desbloqueados (false).
     */
    public void bloquearParametrosPuntuacion(boolean estado) {
        vista.cbRatingJuego.setEnabled(!estado);
        vista.tfRatingFuente.setEnabled(!estado);
        vista.tfRatingPuntuacion.setEnabled(!estado);
        vista.dpRatingFecha.setEnabled(!estado);
        vista.bRatingAgregar.setEnabled(!estado);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con los desarrolladores.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionDev(boolean habilitar) {
        vista.bDevEfectuarModificacion.setVisible(habilitar);
        vista.bDevCancelarModificacion.setVisible(habilitar);

        vista.bDevAgregar.setEnabled(!habilitar);
        vista.bDevModificar.setEnabled(!habilitar);
        vista.bDevEliminar.setEnabled(!habilitar);
        vista.bDevLimpiar.setEnabled(!habilitar);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con los videojuegos.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionJuego(boolean habilitar) {
        vista.bJuegoEfectuarModificacion.setVisible(habilitar);
        vista.bJuegoCancelarModificacion.setVisible(habilitar);

        vista.bJuegoAgregar.setEnabled(!habilitar);
        vista.bJuegoModificar.setEnabled(!habilitar);
        vista.bJuegoEliminar.setEnabled(!habilitar);
        vista.bJuegoLimpiar.setEnabled(!habilitar);
    }

    /**
     * Habilita o deshabilita los botones de modificación relacionados con las puntuaciones.
     * Si el parámetro 'habilitar' es true, se habilitan los botones de modificación; si es false, se deshabilitan.
     * @param habilitar Determina si se habilita (true) o deshabilita (false) el modo de modificación.
     */
    public void habilitarModoModificacionPuntuacion(boolean habilitar) {
        vista.bRatingEfectuarModificacion.setVisible(habilitar);
        vista.bRatingCancelarModificacion.setVisible(habilitar);

        vista.bRatingAgregar.setEnabled(!habilitar);
        vista.bRatingModificar.setEnabled(!habilitar);
        vista.bRatingEliminar.setEnabled(!habilitar);
        vista.bRatingLimpiar.setEnabled(!habilitar);
    }

    /**
     * Limpia los campos de entrada relacionados con los desarrolladores en la vista.
     * Restablece los campos de texto y las selecciones a sus valores por defecto.
     */
    private void limpiarCamposDesarrollador() {
        vista.tfDevNombre.setText(null);
        vista.tfDevEmail.setText(null);
        vista.tfDevExperiencia.setText(null);
        vista.cbDevTipoDesarrollo.setSelectedIndex(0);
    }

    /**
     * Limpia los campos de entrada relacionados con los videojuegos en la vista.
     * Restablece los campos de texto, las selecciones y la lista de desarrolladores asociados al videojuego a sus valores por defecto.
     */
    public void limpiarCamposVideojuego() {
        vista.tfJuegoTitulo.setText(null);
        vista.cbJuegoGenero.setSelectedIndex(0);
        vista.tfJuegoPrecio.setText(null);
        vista.rbJuegoPc.setSelected(true);
        vista.dpJuegoLanzamiento.setText(null);
        vista.cbJuegoDesarrolladores.setSelectedIndex(0);
        vista.dlmDevsDeJuego.clear();
    }

    /**
     * Limpia los campos de entrada relacionados con las puntuaciones en la vista.
     * Restablece los campos de texto y las selecciones a sus valores por defecto.
     */
    public void limpiarCamposPuntuacion() {
        vista.cbRatingJuego.setSelectedIndex(0);
        vista.tfRatingFuente.setText(null);
        vista.tfRatingPuntuacion.setText(null);
        vista.dpRatingFecha.setText(null);
    }

    /**
     * Establece los desarrolladores de un videojuego seleccionado en la vista.
     * Este método limpia la lista de desarrolladores asociados al videojuego
     * y luego agrega los desarrolladores correspondientes al videojuego seleccionado
     * en la tabla o lista.
     * @param fila La fila de la tabla de videojuegos que contiene el videojuego seleccionado.
     */
    private void setDesarrolladoresDeUnJuego(int fila) {
        ObjectId objectIdJuego = new ObjectId(String.valueOf(vista.dtmJuegos.getValueAt(fila, 0)));
        Videojuego juegoSeleccionado = modelo.getJuegoById(objectIdJuego);
        vista.dlmDevsDeJuego.clear();
        for (Desarrollador unDesarrollador : juegoSeleccionado.getDesarrolladores()) {
            vista.dlmDevsDeJuego.addElement(unDesarrollador);
        }
    }

    /**
     * Establece la plataforma de un videojuego en la vista.
     * Marca la plataforma del videojuego como seleccionada en los botones de opción
     * (Radio Buttons) según el valor proporcionado.
     * @param plataformaVideojuego La plataforma del videojuego. Puede ser "PC", "Playstation", o "Xbox".
     */
    private void setPlataformaVideojuego(String plataformaVideojuego) {
        switch (plataformaVideojuego) {
            case "PC":
                vista.rbJuegoPc.setSelected(true);
                break;
            case "Playstation":
                vista.rbJuegoPlaystation.setSelected(true);
                break;
            case "Xbox":
                vista.rbJuegoXbox.setSelected(true);
                break;
        }
    }

    /**
     * Selecciona el índice del juego en el ComboBox de clasificación de juegos,
     * basándose en el nombre del juego obtenido de una fila específica en la tabla de puntuaciones.
     * @param fila El número de fila en la tabla de puntuaciones de donde se obtiene el nombre del juego.
     * @return El índice del juego en el ComboBox si se encuentra una coincidencia, o -1 si no se encuentra.
     */
    private int elegirJuegoComboBox(int fila) {
        String nombreTabla = String.valueOf(vista.dtmPuntuaciones.getValueAt(fila, 1));
        for (int i = 0; i < vista.cbRatingJuego.getItemCount(); i++) {
            String nombreJuego = vista.cbRatingJuego.getItemAt(i).toString();
            if (nombreJuego.equals(nombreTabla)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Agrega los desarrolladores asociados a un videojuego en la vista
     * a una lista de desarrolladores.
     * Este método recorre la lista de elementos en la vista y los agrega a una nueva lista.
     * @return Una lista de desarrolladores asociados al videojuego.
     */
    private List<Desarrollador> agregarDevsAJuego() {
        List<Desarrollador> listaDesarrolladores = new ArrayList<>();
        for (int i = 0; i < vista.dlmDevsDeJuego.size(); i++) {
            listaDesarrolladores.add(vista.dlmDevsDeJuego.getElementAt(i));
        }
        return listaDesarrolladores;
    }

    /**
     * Obtiene la plataforma seleccionada para un videojuego en la vista.
     * Este método verifica cuál de los botones de radio de plataforma está seleccionado
     * y devuelve el nombre de la plataforma correspondiente.
     * @return El nombre de la plataforma seleccionada, que puede ser "PC", "Playstation" o "Xbox".
     */
    private String elegirPlataforma() {
        String plataforma = "";
        if (vista.rbJuegoPc.isSelected()) plataforma = "PC";
        if (vista.rbJuegoPlaystation.isSelected()) plataforma = "Playstation";
        if (vista.rbJuegoXbox.isSelected()) plataforma = "Xbox";

        return plataforma;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.tfDevBuscar) {
            listarDesarrolladores(modelo.getDevs(vista.tfDevBuscar.getText()));
        } else if (e.getSource() == vista.tfJuegoBuscar) {
            listarVideojuegos(modelo.getJuegos(vista.tfJuegoBuscar.getText()));
        } else if (e.getSource() == vista.tfRatingBuscar) {
            listarPuntuaciones(modelo.getRatings(vista.tfRatingBuscar.getText()));
        }
    }
}