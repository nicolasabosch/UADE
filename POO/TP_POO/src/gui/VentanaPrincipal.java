package gui;

import base.Comentario;
import base.Contenido;
import base.IInteractuable;
import base.PlanSuscripcion;
import base.Playlist;
import base.Usuario;
import excepciones.UadeBeatsException;
import modulo.Cancion;
import modulo.CatalogoMusical;
import modulo.EntrevistaGrabada;
import persistencia.GestorPersistencia;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Ventana principal de la aplicacion grafica del Modulo Musica y Videos.
 * Presenta cuatro paneles conectados con la logica del modelo:
 *  1) Catalogo y reproduccion (con validacion de plan del usuario).
 *  2) Playlists privadas de cada usuario.
 *  3) Interacciones sociales (like / comentar / compartir) y persistencia.
 *  4) Cambio de plan de suscripcion (FREE, PREMIUM, ARTIST_PASS).
 *
 * Sigue la regla de arquitectura vista en clase: la GUI solo dibuja la pantalla
 * y captura acciones del usuario; cada boton delega en un metodo privado que invoca la logica
 * del modelo (Contenido, CatalogoMusical, GestorPersistencia). No contiene reglas
 * de negocio. Usa unicamente los layout managers de java.awt (BorderLayout,
 * GridLayout y FlowLayout).
 */
public class VentanaPrincipal {

    private final JFrame ventana;
    private final CatalogoMusical catalogo;
    private final List<Usuario> usuarios;
    private final GestorPersistencia persistencia;
    private final String rutaComentarios;
    private final String rutaUsuarios;

    // Componentes del panel Catalogo
    private JComboBox<Contenido> comboContenidoCat;
    private JComboBox<Usuario> comboUsuarioCat;
    private JTextArea consolaCat;

    // Componentes del panel Playlists
    private JComboBox<Usuario> comboUsuarioPlaylist;
    private JComboBox<Playlist> comboPlaylist;
    private JTextArea consolaPlaylist;
    private JButton botonVerPlaylist;
    private JButton botonReproducirPlaylist;

    // Componentes del panel Interacciones
    private JComboBox<Contenido> comboContenidoInt;
    private JComboBox<Usuario> comboUsuarioInt;
    private JTextField campoComentario;
    private JTextArea consolaInt;
    private JButton botonLike;
    private JButton botonComentar;
    private JButton botonCompartir;

    // Componentes del panel Plan
    private JComboBox<Usuario> comboUsuarioPlan;
    private JComboBox<PlanSuscripcion> comboPlan;
    private JTextArea consolaPlan;

    public VentanaPrincipal(CatalogoMusical catalogo, List<Usuario> usuarios,
                            List<Playlist> playlists, GestorPersistencia persistencia,
                            String rutaComentarios, String rutaUsuarios) {
        this.catalogo = catalogo;
        this.usuarios = usuarios;
        this.persistencia = persistencia;
        this.rutaComentarios = rutaComentarios;
        this.rutaUsuarios = rutaUsuarios;

        ventana = new JFrame("UADE Beats - Modulo Musica y Videos (Equipo 7)");
        ventana.setSize(1220, 680);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenedor = new JPanel(new BorderLayout(8, 8));
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelesSuperiores = new JPanel(new GridLayout(1, 3, 8, 0));
        panelesSuperiores.add(construirPanelCatalogo());
        panelesSuperiores.add(construirPanelPlaylists());
        panelesSuperiores.add(construirPanelInteracciones());
        contenedor.add(panelesSuperiores, BorderLayout.CENTER);
        contenedor.add(construirPanelPlan(), BorderLayout.SOUTH);
        ventana.add(contenedor);
    }

    public void setVisible(boolean visible) {
        ventana.setVisible(visible);
    }

    // ---------------------- Construccion de la interfaz ----------------------

    private JPanel construirPanelCatalogo() {
        comboContenidoCat = new JComboBox<>(
                new DefaultComboBoxModel<>(catalogo.getContenidos().toArray(new Contenido[0])));
        comboUsuarioCat = new JComboBox<>(
                new DefaultComboBoxModel<>(usuarios.toArray(new Usuario[0])));
        consolaCat = crearConsola();

        JPanel formulario = new JPanel(new GridLayout(0, 1, 4, 4));
        formulario.add(new JLabel("Contenido:"));
        formulario.add(comboContenidoCat);
        formulario.add(new JLabel("Usuario:"));
        formulario.add(comboUsuarioCat);

        JButton botonReproducir = new JButton("Reproducir");
        JButton botonDetalle = new JButton("Ver detalle");
        JButton botonComentariosCat = new JButton("Ver comentarios");
        JButton botonDespejarCat = new JButton("Despejar");
        botonReproducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSeleccion();
            }
        });
        botonDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalleSeleccion();
            }
        });
        botonComentariosCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verComentariosCatalogo();
            }
        });
        botonDespejarCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                despejarConsola(consolaCat);
            }
        });

        JPanel botones = new JPanel(new GridLayout(2, 2, 4, 4));
        botones.add(botonReproducir);
        botones.add(botonDetalle);
        botones.add(botonComentariosCat);
        botones.add(botonDespejarCat);

        return ensamblar("Catalogo y reproduccion", formulario, botones, consolaCat);
    }

    private JPanel construirPanelPlaylists() {
        comboUsuarioPlaylist = new JComboBox<>(
                new DefaultComboBoxModel<>(usuarios.toArray(new Usuario[0])));
        comboPlaylist = new JComboBox<>(new DefaultComboBoxModel<>());
        consolaPlaylist = crearConsola();

        comboUsuarioPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarComboPlaylists();
            }
        });
        comboPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoPlaylists();
            }
        });

        JPanel formulario = new JPanel(new GridLayout(0, 1, 4, 4));
        formulario.add(new JLabel("Usuario:"));
        formulario.add(comboUsuarioPlaylist);
        formulario.add(new JLabel("Playlist:"));
        formulario.add(comboPlaylist);

        botonVerPlaylist = new JButton("Ver playlist");
        botonReproducirPlaylist = new JButton("Reproducir");
        JButton botonDespejarPlaylist = new JButton("Despejar");
        botonVerPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verPlaylistSeleccion();
            }
        });
        botonReproducirPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirPlaylistSeleccion();
            }
        });
        botonDespejarPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                despejarConsola(consolaPlaylist);
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(botonVerPlaylist);
        botones.add(botonReproducirPlaylist);
        botones.add(botonDespejarPlaylist);

        actualizarComboPlaylists();
        return ensamblar("Playlists", formulario, botones, consolaPlaylist);
    }

    private JPanel construirPanelInteracciones() {
        DefaultComboBoxModel<Contenido> modeloInteractuables = new DefaultComboBoxModel<>();
        for (Contenido c : catalogo.getContenidos()) {
            if (c instanceof IInteractuable) {
                modeloInteractuables.addElement(c);
            }
        }
        comboContenidoInt = new JComboBox<>(modeloInteractuables);
        comboUsuarioInt = new JComboBox<>(
                new DefaultComboBoxModel<>(usuarios.toArray(new Usuario[0])));
        campoComentario = new JTextField();
        consolaInt = crearConsola();

        JPanel formulario = new JPanel(new GridLayout(0, 1, 4, 4));
        formulario.add(new JLabel("Contenido interactuable:"));
        formulario.add(comboContenidoInt);
        formulario.add(new JLabel("Usuario:"));
        formulario.add(comboUsuarioInt);
        formulario.add(new JLabel("Comentario:"));
        formulario.add(campoComentario);

        botonLike = new JButton("Like");
        botonComentar = new JButton("Comentar");
        botonCompartir = new JButton("Compartir");
        JButton botonCargar = new JButton("Cargar comentarios");
        JButton botonDespejarInt = new JButton("Despejar");

        ActionListener actualizarInteracciones = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoInteracciones();
            }
        };
        comboContenidoInt.addActionListener(actualizarInteracciones);
        comboUsuarioInt.addActionListener(actualizarInteracciones);
        botonLike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darLikeSeleccion();
            }
        });
        botonComentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comentarSeleccion();
            }
        });
        botonCompartir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compartirSeleccion();
            }
        });
        botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarComentarios();
            }
        });
        botonDespejarInt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                despejarConsola(consolaInt);
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(botonLike);
        botones.add(botonComentar);
        botones.add(botonCompartir);
        botones.add(botonCargar);
        botones.add(botonDespejarInt);

        actualizarEstadoInteracciones();
        return ensamblar("Interacciones", formulario, botones, consolaInt);
    }

    private JPanel construirPanelPlan() {
        comboUsuarioPlan = new JComboBox<>(
                new DefaultComboBoxModel<>(usuarios.toArray(new Usuario[0])));
        comboPlan = new JComboBox<>(
                new DefaultComboBoxModel<>(PlanSuscripcion.values()));
        consolaPlan = crearConsola();
        consolaPlan.setRows(3);

        JPanel formulario = new JPanel(new GridLayout(0, 1, 4, 4));
        formulario.add(new JLabel("Usuario:"));
        formulario.add(comboUsuarioPlan);
        formulario.add(new JLabel("Nuevo plan:"));
        formulario.add(comboPlan);

        JButton botonCambiarPlan = new JButton("Cambiar plan");
        JButton botonDespejarPlan = new JButton("Despejar");
        botonCambiarPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarPlanUsuario();
            }
        });
        botonDespejarPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                despejarConsola(consolaPlan);
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(botonCambiarPlan);
        botones.add(botonDespejarPlan);

        return ensamblar("Plan de suscripcion", formulario, botones, consolaPlan);
    }

    private JPanel ensamblar(String titulo, JPanel formulario, JPanel botones, JTextArea consola) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(botones, BorderLayout.SOUTH);
        panel.add(arriba, BorderLayout.NORTH);
        panel.add(new JScrollPane(consola), BorderLayout.CENTER);
        return panel;
    }

    private JTextArea crearConsola() {
        JTextArea consola = new JTextArea();
        consola.setEditable(false);
        consola.setLineWrap(true);
        consola.setWrapStyleWord(true);
        return consola;
    }

    // ---------------------- Logica de los botones (delegada) ----------------------

    private void reproducirSeleccion() {
        final Contenido contenido = (Contenido) comboContenidoCat.getSelectedItem();
        final Usuario usuario = (Usuario) comboUsuarioCat.getSelectedItem();
        if (contenido == null || usuario == null) {
            return;
        }
        try {
            agregarSalida(consolaCat, contenido.reproducir(usuario));
        } catch (UadeBeatsException ex) {
            agregarSalida(consolaCat, "ACCESO DENEGADO: " + ex.getMessage());
        }
    }

    private void verDetalleSeleccion() {
        final Contenido contenido = (Contenido) comboContenidoCat.getSelectedItem();
        if (contenido != null) {
            agregarSalida(consolaCat, contenido.mostrarDetalle());
        }
    }

    private void verComentariosCatalogo() {
        final Contenido contenido = (Contenido) comboContenidoCat.getSelectedItem();
        if (contenido != null) {
            mostrarComentarios(contenido, consolaCat);
        }
    }

    private void actualizarComboPlaylists() {
        Usuario usuario = (Usuario) comboUsuarioPlaylist.getSelectedItem();
        DefaultComboBoxModel<Playlist> modelo = new DefaultComboBoxModel<>();
        if (usuario != null) {
            for (Playlist playlist : usuario.getPlaylists()) {
                modelo.addElement(playlist);
            }
        }
        comboPlaylist.setModel(modelo);
        actualizarEstadoPlaylists();
    }

    private void actualizarEstadoPlaylists() {
        boolean hayPlaylist = comboPlaylist.getSelectedItem() != null;
        botonVerPlaylist.setEnabled(hayPlaylist);
        botonReproducirPlaylist.setEnabled(hayPlaylist);
    }

    private void verPlaylistSeleccion() {
        Playlist playlist = (Playlist) comboPlaylist.getSelectedItem();
        if (playlist != null) {
            agregarSalida(consolaPlaylist, detallePlaylist(playlist));
        }
    }

    private void reproducirPlaylistSeleccion() {
        Playlist playlist = (Playlist) comboPlaylist.getSelectedItem();
        Usuario usuario = (Usuario) comboUsuarioPlaylist.getSelectedItem();
        if (playlist == null || usuario == null) {
            return;
        }
        agregarSalida(consolaPlaylist, playlist.reproducirToda(usuario));
    }

    private String detallePlaylist(Playlist playlist) {
        String salto = System.lineSeparator();
        String detalle = playlist.mostrarDetalle() + salto + "Lista de contenidos:";
        if (playlist.getContenidos().isEmpty()) {
            return detalle + salto + "  (Sin contenidos cargados)";
        }
        int numero = 1;
        for (Contenido contenido : playlist.getContenidos()) {
            detalle = detalle + salto + "  " + numero + ". " + contenido.getTitulo();
            if (contenido instanceof Cancion) {
                Cancion cancion = (Cancion) contenido;
                detalle = detalle + " - " + cancion.getNombreArtista();
            }
            detalle = detalle + " (" + contenido.getDuracionSegundos() + "s)";
            numero = numero + 1;
        }
        return detalle;
    }

    private void darLikeSeleccion() {
        final IInteractuable item = (IInteractuable) comboContenidoInt.getSelectedItem();
        final Usuario usuario = (Usuario) comboUsuarioInt.getSelectedItem();
        if (!puedeInteractuarCon((Contenido) item, usuario)) {
            agregarSalida(consolaInt, mensajeAccesoDenegado((Contenido) item, usuario));
            return;
        }
        if (item != null && usuario != null) {
            agregarSalida(consolaInt, item.darLike(usuario));
        }
    }

    private void comentarSeleccion() {
        final IInteractuable item = (IInteractuable) comboContenidoInt.getSelectedItem();
        final Usuario usuario = (Usuario) comboUsuarioInt.getSelectedItem();
        if (item == null || usuario == null) {
            return;
        }
        if (!puedeInteractuarCon((Contenido) item, usuario)) {
            agregarSalida(consolaInt, mensajeAccesoDenegado((Contenido) item, usuario));
            return;
        }
        final String mensaje = campoComentario.getText();
        boolean comentarioRegistrado = false;
        try {
            agregarSalida(consolaInt, item.comentar(usuario, mensaje));
            comentarioRegistrado = true;
        } catch (IllegalArgumentException ex) {
            agregarSalida(consolaInt, "No se pudo comentar: " + ex.getMessage());
        }
        if (comentarioRegistrado && guardarComentariosEnArchivo()) {
            agregarSalida(consolaInt, "Comentario guardado automaticamente en " + rutaComentarios);
        }
        campoComentario.setText("");
    }

    private void compartirSeleccion() {
        final IInteractuable item = (IInteractuable) comboContenidoInt.getSelectedItem();
        final Usuario usuario = (Usuario) comboUsuarioInt.getSelectedItem();
        if (!puedeInteractuarCon((Contenido) item, usuario)) {
            agregarSalida(consolaInt, mensajeAccesoDenegado((Contenido) item, usuario));
            return;
        }
        if (item != null && usuario != null) {
            agregarSalida(consolaInt, item.compartir());
        }
    }

    private boolean guardarComentariosEnArchivo() {
        try {
            persistencia.guardarComentarios(catalogo, rutaComentarios);
            return true;
        } catch (IOException ex) {
            consolaInt.append("Error al guardar: " + ex.getMessage() + "\n");
            return false;
        }
    }

    private void cambiarPlanUsuario() {
        Usuario usuario = (Usuario) comboUsuarioPlan.getSelectedItem();
        PlanSuscripcion nuevoPlan = (PlanSuscripcion) comboPlan.getSelectedItem();
        if (usuario == null || nuevoPlan == null) {
            agregarSalida(consolaPlan, "Seleccione un usuario y un plan.");
            return;
        }
        PlanSuscripcion planAnterior = usuario.getPlan();
        try {
            if (usuario.cambiarPlan(nuevoPlan)) {
                if (guardarUsuariosEnArchivo()) {
                    refrescarCombosUsuarios(usuario.getNombreUsuario());
                    actualizarComboPlaylists();
                    actualizarEstadoInteracciones();
                    agregarSalida(consolaPlan, "Plan actualizado: " + usuario.getNombreUsuario()
                            + " paso de " + planAnterior + " a " + nuevoPlan + ".");
                    agregarSalida(consolaPlan, "Cambio guardado en " + rutaUsuarios);
                }
            } else {
                agregarSalida(consolaPlan, usuario.getNombreUsuario()
                        + " ya tiene el plan " + nuevoPlan + ".");
            }
        } catch (IllegalArgumentException ex) {
            agregarSalida(consolaPlan, "No se pudo cambiar el plan: " + ex.getMessage());
        }
    }

    private boolean guardarUsuariosEnArchivo() {
        try {
            persistencia.guardarUsuarios(usuarios, rutaUsuarios);
            return true;
        } catch (IOException ex) {
            consolaPlan.append("Error al guardar: " + ex.getMessage() + "\n");
            return false;
        }
    }

    private void refrescarCombosUsuarios(String nombreUsuarioSeleccionado) {
        Usuario[] arrayUsuarios = usuarios.toArray(new Usuario[0]);
        refrescarComboUsuario(comboUsuarioCat, arrayUsuarios, nombreUsuarioSeleccionado);
        refrescarComboUsuario(comboUsuarioPlaylist, arrayUsuarios, nombreUsuarioSeleccionado);
        refrescarComboUsuario(comboUsuarioInt, arrayUsuarios, nombreUsuarioSeleccionado);
        refrescarComboUsuario(comboUsuarioPlan, arrayUsuarios, nombreUsuarioSeleccionado);
    }

    private void refrescarComboUsuario(JComboBox<Usuario> combo, Usuario[] arrayUsuarios,
                                       String nombreUsuarioSeleccionado) {
        DefaultComboBoxModel<Usuario> modelo = new DefaultComboBoxModel<>(arrayUsuarios);
        combo.setModel(modelo);
        for (int i = 0; i < modelo.getSize(); i++) {
            Usuario u = modelo.getElementAt(i);
            if (u.getNombreUsuario().equals(nombreUsuarioSeleccionado)) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void cargarComentarios() {
        final Contenido contenido = (Contenido) comboContenidoInt.getSelectedItem();
        if (contenido != null) {
            mostrarComentarios(contenido, consolaInt);
        }
    }

    private void mostrarComentarios(Contenido contenido, JTextArea consola) {
        try {
            List<String> recuperados = persistencia.cargarComentarios(
                    rutaComentarios, contenido.getTitulo());
            consola.append("--- Comentarios de '" + contenido.getTitulo() + "' ---\n");
            if (recuperados.isEmpty()) {
                if (contenido.getComentarios().isEmpty()) {
                    consola.append("(Sin comentarios para este contenido)\n");
                } else {
                    for (Comentario comentario : contenido.getComentarios()) {
                        consola.append(comentario.toString() + "\n");
                    }
                }
            } else {
                for (String linea : recuperados) {
                    consola.append(linea + "\n");
                }
            }
        } catch (IOException ex) {
            consola.append("Error al cargar: " + ex.getMessage() + "\n");
        }
    }

    private void actualizarEstadoInteracciones() {
        Contenido contenido = (Contenido) comboContenidoInt.getSelectedItem();
        Usuario usuario = (Usuario) comboUsuarioInt.getSelectedItem();
        boolean habilitado = puedeInteractuarCon(contenido, usuario);
        botonLike.setEnabled(habilitado);
        botonComentar.setEnabled(habilitado);
        botonCompartir.setEnabled(habilitado);
        campoComentario.setEnabled(habilitado);
    }

    private boolean puedeInteractuarCon(Contenido contenido, Usuario usuario) {
        if (contenido == null || usuario == null) {
            return false;
        }
        if (!contenido.isDisponible()) {
            return false;
        }
        if (contenido instanceof EntrevistaGrabada && !usuario.accederEntrevista()) {
            return false;
        }
        return usuario.accederContenido(contenido.isExclusivo());
    }

    private String mensajeAccesoDenegado(Contenido contenido, Usuario usuario) {
        if (contenido == null || usuario == null) {
            return "ACCESO DENEGADO: seleccione contenido y usuario.";
        }
        if (!contenido.isDisponible()) {
            return "ACCESO DENEGADO: el contenido '" + contenido.getTitulo()
                    + "' no esta disponible.";
        }
        if (contenido instanceof EntrevistaGrabada) {
            return "ACCESO DENEGADO: el plan " + usuario.getPlan()
                    + " no permite interactuar con entrevistas (se requiere ARTIST_PASS).";
        }
        return "ACCESO DENEGADO: el plan " + usuario.getPlan()
                + " no permite interactuar con contenido exclusivo.";
    }

    private void despejarConsola(JTextArea consola) {
        consola.setText("");
    }

    private void agregarSalida(JTextArea consola, String texto) {
        if (texto != null && !texto.trim().isEmpty()) {
            consola.append(texto + "\n");
        }
    }
}
