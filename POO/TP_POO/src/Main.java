import base.Artista;
import base.Contenido;
import base.IInteractuable;
import base.PlanSuscripcion;
import base.Playlist;
import base.Usuario;
import excepciones.UadeBeatsException;
import gui.VentanaPrincipal;
import modulo.Cancion;
import modulo.CatalogoMusical;
import persistencia.GestorPersistencia;

import javax.swing.SwingUtilities;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Clase principal de demostracion del Modulo Musica y Videos de UADE Beats.
 * Equipo 7 - Comision Aula 349 - Paradigma Orientado a Objetos (UADE).
 *
 * Ejecuta una prueba funcional que recorre el flujo principal del modulo y
 * muestra distintos casos (exitosos y rechazados) y, al finalizar, abre la
 * interfaz grafica si el entorno lo permite.
 */
public class Main {

    private static final String CARPETA_DATOS = buscarCarpetaDatos();
    private static final String RUTA_ARTISTAS = CARPETA_DATOS + File.separator + "artistas.txt";
    private static final String RUTA_USUARIOS = CARPETA_DATOS + File.separator + "usuarios.txt";
    private static final String RUTA_CANCIONES = CARPETA_DATOS + File.separator + "canciones.txt";
    private static final String RUTA_VIDEOCLIPS = CARPETA_DATOS + File.separator + "videoclips.txt";
    private static final String RUTA_ALBUMES = CARPETA_DATOS + File.separator + "albumes.txt";
    private static final String RUTA_ENTREVISTAS = CARPETA_DATOS + File.separator + "entrevistas.txt";
    private static final String RUTA_PLAYLIST = CARPETA_DATOS + File.separator + "playlist.txt";
    private static final String RUTA_COMENTARIOS = CARPETA_DATOS + File.separator + "comentarios.txt";

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("   UADE Beats - Modulo Musica y Videos - Equipo 7");
        System.out.println("   Prueba funcional (Entrega Final - POO UADE)");
        System.out.println("============================================================\n");

        // -------------------- Carga de datos desde archivos --------------------
        GestorPersistencia gestor = new GestorPersistencia();
        List<Artista> artistas;
        List<Usuario> usuarios;
        CatalogoMusical catalogo;
        List<Playlist> playlists;
        try {
            artistas = gestor.cargarArtistas(RUTA_ARTISTAS);
            usuarios = gestor.cargarUsuarios(RUTA_USUARIOS);
            catalogo = gestor.cargarCatalogo(
                    RUTA_CANCIONES, RUTA_VIDEOCLIPS, RUTA_ALBUMES, RUTA_ENTREVISTAS, artistas);
            playlists = gestor.cargarPlaylists(RUTA_PLAYLIST, usuarios, catalogo);
            System.out.println("Datos cargados desde archivos en carpeta '" + CARPETA_DATOS + "'.\n");
        } catch (IOException e) {
            System.out.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
            return;
        }

        Usuario usuarioFree = usuarioPorId(usuarios, 1);
        Usuario usuarioPremium = usuarioPorId(usuarios, 2);
        Usuario usuarioArtistPass = usuarioPorId(usuarios, 3);
        Contenido cancion1 = contenidoPorId(catalogo, 101);
        Contenido cancion3 = contenidoPorId(catalogo, 103);
        Contenido videoclip1 = contenidoPorId(catalogo, 201);
        Contenido entrevista = contenidoPorId(catalogo, 401);

        // -------------------- 1) Reproduccion: caso exitoso --------------------
        System.out.println("[1] Reproduccion exitosa (contenido libre, usuario FREE)");
        ejecutar(new AccionRiesgosa() {
            @Override
            public String ejecutar() throws UadeBeatsException {
                return cancion1.reproducir(usuarioFree);
            }
        });

        // -------------------- 2) Rechazo por plan insuficiente --------------------
        System.out.println("\n[2] Rechazo por plan insuficiente (contenido exclusivo, usuario FREE)");
        ejecutar(new AccionRiesgosa() {
            @Override
            public String ejecutar() throws UadeBeatsException {
                return cancion3.reproducir(usuarioFree);
            }
        });

        System.out.println("    Reintento con usuario PREMIUM:");
        ejecutar(new AccionRiesgosa() {
            @Override
            public String ejecutar() throws UadeBeatsException {
                return cancion3.reproducir(usuarioPremium);
            }
        });

        // -------------------- 3) Entrevistas: disponibilidad y plan ARTIST_PASS --------------------
        System.out.println("\n[3] Entrevistas (solo ARTIST_PASS)");
        System.out.println("    3a) Entrevista no disponible:");
        ejecutar(new AccionRiesgosa() {
            @Override
            public String ejecutar() throws UadeBeatsException {
                return entrevista.reproducir(usuarioArtistPass);
            }
        });

        Contenido entrevistaDisponible = contenidoPorId(catalogo, 402);
        System.out.println("    3b) Usuario PREMIUM (plan no habilita entrevistas):");
        ejecutar(new AccionRiesgosa() {
            @Override
            public String ejecutar() throws UadeBeatsException {
                return entrevistaDisponible.reproducir(usuarioPremium);
            }
        });

        System.out.println("    3c) Usuario ARTIST_PASS (acceso exitoso):");
        ejecutar(new AccionRiesgosa() {
            @Override
            public String ejecutar() throws UadeBeatsException {
                return entrevistaDisponible.reproducir(usuarioArtistPass);
            }
        });
        System.out.println("    3d) Like en entrevista (ARTIST_PASS):");
        System.out.println(((IInteractuable) entrevistaDisponible).darLike(usuarioArtistPass));

        // -------------------- 4) Interacciones sociales --------------------
        System.out.println("\n[4] Interacciones sociales (IInteractuable)");
        System.out.println(((IInteractuable) cancion1).darLike(usuarioFree));
        System.out.println(((IInteractuable) cancion1).darLike(usuarioPremium));
        System.out.println(((IInteractuable) cancion1).comentar(usuarioPremium, "Tema clasico!"));
        System.out.println(((IInteractuable) videoclip1).darLike(usuarioArtistPass));
        System.out.println(((IInteractuable) videoclip1).compartir());

        // -------------------- 5) Colecciones (List, Map, Set, Comparable, Comparator)
        // --------------------
        System.out.println("\n[5] Colecciones y ordenamientos");
        System.out.println("    Orden natural por titulo (Comparable):");
        for (Contenido c : catalogo.listarOrdenadoPorTitulo()) {
            System.out.println("      - " + c.getTitulo());
        }
        System.out.println("    Orden por duracion (Comparator):");
        for (Contenido c : catalogo.listarOrdenadoPorDuracion()) {
            System.out.println("      - " + c.getTitulo() + " (" + c.getDuracionSegundos() + "s)");
        }
        System.out.println("    Busqueda por id 201 (Map): "
                + catalogo.buscarPorId(201).getTitulo());
        System.out.println("    Generos unicos (Set): " + catalogo.getGeneros());

        // -------------------- 6) Playlist --------------------
        System.out.println("\n[6] Playlist");
        usuarioPremium.agregarAFavoritos(cancion1);
        Playlist playlistDemo = playlistPorId(playlists, 1);
        System.out.println(playlistDemo.reproducirToda(usuarioPremium));
        System.out.println("    Duracion total: " + playlistDemo.duracionTotalSegundos() + "s");

        // -------------------- 7) Persistencia en archivo de texto --------------------
        System.out.println("\n[7] Persistencia en archivo de texto");
        try {
            new File(CARPETA_DATOS).mkdirs();
            gestor.guardarCanciones(catalogo, RUTA_CANCIONES);
            gestor.guardarComentarios(catalogo, RUTA_COMENTARIOS);
            System.out.println("    Catalogo y comentarios guardados en carpeta '" + CARPETA_DATOS + "'.");

            List<Cancion> recuperadas = gestor.cargarCanciones(RUTA_CANCIONES, artistas);
            System.out.println("    Canciones recuperadas del archivo: " + recuperadas.size());
            for (Cancion c : recuperadas) {
                System.out.println("      - " + c.getTitulo() + " / " + c.getNombreArtista());
            }
            List<String> comentarios = gestor.cargarComentarios(RUTA_COMENTARIOS);
            System.out.println("    Comentarios recuperados del archivo:");
            for (String linea : comentarios) {
                System.out.println("      " + linea);
            }
        } catch (IOException e) {
            System.out.println("    Error de persistencia: " + e.getMessage());
        }

        // -------------------- 8) Cambio de plan de suscripcion (maquina de estados) --------------------
        System.out.println("\n[8] Cambio de plan de suscripcion (transiciones de estado)");
        System.out.println("    Estado inicial de juan_free: " + usuarioFree.getPlan());

        usuarioFree.cambiarPlan(PlanSuscripcion.PREMIUM);
        System.out.println("    cambiarPlan(PREMIUM)     -> estado: " + usuarioFree.getPlan());

        usuarioFree.cambiarPlan(PlanSuscripcion.ARTIST_PASS);
        System.out.println("    cambiarPlan(ARTIST_PASS) -> estado: " + usuarioFree.getPlan());

        boolean huboCambio = usuarioFree.cambiarPlan(PlanSuscripcion.ARTIST_PASS);
        System.out.println("    cambiarPlan(ARTIST_PASS) de nuevo: "
                + (huboCambio ? "cambio" : "rechazado, ya esta en ese plan (guarda de transicion)"));

        usuarioFree.cambiarPlan(PlanSuscripcion.FREE);
        System.out.println("    cambiarPlan(FREE)        -> estado: " + usuarioFree.getPlan());

        try {
            gestor.guardarUsuarios(usuarios, RUTA_USUARIOS);
            System.out.println("    Estado de los usuarios guardado en '" + RUTA_USUARIOS + "'.");
        } catch (IOException e) {
            System.out.println("    Error al guardar usuarios: " + e.getMessage());
        }

        System.out.println("\n============================================================");
        System.out.println("   Fin de la prueba funcional.");
        System.out.println("============================================================");

        // -------------------- 9) Interfaz grafica --------------------

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal(
                        catalogo, usuarios, playlists, gestor, RUTA_COMENTARIOS, RUTA_USUARIOS);
                ventana.setVisible(true);
            }
        });
    }

    /** Resuelve la carpeta src/datos segun desde donde se ejecuta (Clase 11/12: File.exists()). */
    private static String buscarCarpetaDatos() {
        String[] candidatos = {
                "src" + File.separator + "datos",
                "datos",
                ".." + File.separator + "src" + File.separator + "datos"
        };
        for (String candidato : candidatos) {
            File carpeta = new File(candidato).getAbsoluteFile();
            if (esCarpetaSrcDatos(carpeta) && new File(carpeta, "artistas.txt").exists()) {
                return candidato;
            }
        }
        return "src" + File.separator + "datos";
    }

    private static boolean esCarpetaSrcDatos(File carpeta) {
        String ruta = carpeta.getAbsolutePath().replace('\\', '/');
        return ruta.endsWith("/src/datos");
    }

    private static Playlist playlistPorId(List<Playlist> playlists, int id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId() == id) {
                return playlist;
            }
        }
        throw new IllegalArgumentException("No se encontro la playlist con id " + id + ".");
    }

    private static Usuario usuarioPorId(List<Usuario> usuarios, int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("No se encontro el usuario con id " + id + ".");
    }

    private static Contenido contenidoPorId(CatalogoMusical catalogo, int id) {
        Contenido contenido = catalogo.buscarPorId(id);
        if (contenido != null) {
            return contenido;
        }
        throw new IllegalArgumentException("No se encontro el contenido con id " + id + ".");
    }

    /**
     * Ejecuta una accion que puede lanzar una excepcion del dominio y muestra
     * el resultado de forma controlada (caso exitoso o rechazado).
     */
    private static void ejecutar(AccionRiesgosa accion) {
        try {
            String resultado = accion.ejecutar();
            if (resultado != null && !resultado.trim().isEmpty()) {
                System.out.println(resultado);
            }
        } catch (UadeBeatsException e) {
            System.out.println("    [RECHAZADO] " + e.getMessage());
        }
    }

    /** Interfaz auxiliar para acciones que pueden fallar. */
    private interface AccionRiesgosa {
        String ejecutar() throws UadeBeatsException;
    }
}
