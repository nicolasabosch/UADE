package persistencia;

import base.Artista;
import base.Comentario;
import base.Contenido;
import base.PlanSuscripcion;
import base.Playlist;
import base.Usuario;
import modulo.Album;
import modulo.Cancion;
import modulo.CatalogoMusical;
import modulo.EntrevistaGrabada;
import modulo.VideoClip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de persistencia en archivos de texto plano (.txt) para UADE Beats.
 * Permite guardar y recuperar informacion del catalogo y de las interacciones,
 * demostrando que los datos no quedan solo en memoria durante la ejecucion.
 *
 * Utiliza lectura clasica con FileReader/BufferedReader y escritura con
 * Files.writeString (NIO.2). Cada linea es un registro y los campos se separan con ';'.
 * El manejo de IOException es obligatorio (excepcion controlada).
 */
public class GestorPersistencia {

    private static final String SEPARADOR = ";";

    /**
     * Carga artistas desde archivo.
     * Formato: id;nombre;generoPrincipal;biografia;verificado
     */
    public List<Artista> cargarArtistas(String ruta) throws IOException {
        List<Artista> artistas = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            String[] campos = separar(linea);
            if (campos.length != 5) {
                continue;
            }
            artistas.add(new Artista(
                    Integer.parseInt(campos[0].trim()),
                    campos[1].trim(),
                    campos[2].trim(),
                    campos[3].trim(),
                    Boolean.parseBoolean(campos[4].trim())));
        }
        return artistas;
    }

    /**
     * Carga usuarios desde archivo.
     * Formato: id;nombreUsuario;nombre;apellido;email;password;plan
     */
    public List<Usuario> cargarUsuarios(String ruta) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            String[] campos = separar(linea);
            if (campos.length != 7) {
                continue;
            }
            usuarios.add(new Usuario(
                    Integer.parseInt(campos[0].trim()),
                    campos[1].trim(),
                    campos[2].trim(),
                    campos[3].trim(),
                    campos[4].trim(),
                    campos[5].trim(),
                    PlanSuscripcion.valueOf(campos[6].trim())));
        }
        return usuarios;
    }

    /**
     * Guarda usuarios en archivo (sobrescribe).
     * Formato por linea: id;nombreUsuario;nombre;apellido;email;password;plan
     */
    public void guardarUsuarios(List<Usuario> usuarios, String ruta) throws IOException {
        String contenidoArchivo = "# id;nombreUsuario;nombre;apellido;email;password;plan"
                + System.lineSeparator();
        for (Usuario usuario : usuarios) {
            contenidoArchivo = contenidoArchivo + usuario.getId() + SEPARADOR
                    + usuario.getNombreUsuario() + SEPARADOR
                    + usuario.getNombre() + SEPARADOR
                    + usuario.getApellido() + SEPARADOR
                    + usuario.getEmail() + SEPARADOR
                    + usuario.getPassword() + SEPARADOR
                    + usuario.getPlan().name()
                    + System.lineSeparator();
        }
        escribir(ruta, contenidoArchivo);
    }

    /**
     * Carga el catalogo completo desde archivos separados por tipo.
     */
    public CatalogoMusical cargarCatalogo(String rutaCanciones, String rutaVideoclips,
                                          String rutaAlbumes, String rutaEntrevistas,
                                          List<Artista> artistas) throws IOException {
        CatalogoMusical catalogo = new CatalogoMusical();

        for (Cancion cancion : cargarCanciones(rutaCanciones, artistas)) {
            catalogo.agregar(cancion);
        }
        for (VideoClip videoclip : cargarVideoclips(rutaVideoclips, catalogo)) {
            catalogo.agregar(videoclip);
        }
        for (Album album : cargarAlbumes(rutaAlbumes, catalogo)) {
            catalogo.agregar(album);
        }
        for (EntrevistaGrabada entrevista : cargarEntrevistas(rutaEntrevistas)) {
            catalogo.agregar(entrevista);
        }
        return catalogo;
    }

    /**
     * Carga canciones fuente desde archivo.
     * Formato: id;titulo;duracion;descripcion;fecha;disponible;exclusivo;genero;artistaId;letra
     */
    public List<Cancion> cargarCanciones(String ruta, List<Artista> artistas) throws IOException {
        List<Cancion> canciones = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            canciones.add(crearCancionFuente(separar(linea), artistas));
        }
        return canciones;
    }

    /**
     * Carga videoclips desde archivo.
     * Formato: id;titulo;duracion;descripcion;fecha;disponible;exclusivo;resolucion;subtitulos;url;cancionId
     */
    public List<VideoClip> cargarVideoclips(String ruta, CatalogoMusical catalogo) throws IOException {
        List<VideoClip> videoclips = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            String[] campos = separar(linea);
            if (campos.length != 11) {
                throw new IOException("Formato invalido para videoclip.");
            }
            Cancion cancion = buscarCancionPorId(catalogo, Integer.parseInt(campos[10].trim()));
            if (cancion == null) {
                throw new IOException("No se encontro la cancion asociada al videoclip.");
            }
            videoclips.add(new VideoClip(
                    Integer.parseInt(campos[0].trim()),
                    campos[1].trim(),
                    Integer.parseInt(campos[2].trim()),
                    campos[3].trim(),
                    campos[4].trim(),
                    Boolean.parseBoolean(campos[5].trim()),
                    Boolean.parseBoolean(campos[6].trim()),
                    campos[7].trim(),
                    Boolean.parseBoolean(campos[8].trim()),
                    campos[9].trim(),
                    cancion));
        }
        return videoclips;
    }

    /**
     * Carga albumes desde archivo.
     * Formato: id;titulo;descripcion;fecha;disponible;exclusivo;anio;discografica;cancionIds
     */
    public List<Album> cargarAlbumes(String ruta, CatalogoMusical catalogo) throws IOException {
        List<Album> albumes = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            String[] campos = separar(linea);
            if (campos.length != 9) {
                throw new IOException("Formato invalido para album.");
            }
            Album album = new Album(
                    Integer.parseInt(campos[0].trim()),
                    campos[1].trim(),
                    campos[2].trim(),
                    campos[3].trim(),
                    Boolean.parseBoolean(campos[4].trim()),
                    Boolean.parseBoolean(campos[5].trim()),
                    Integer.parseInt(campos[6].trim()),
                    campos[7].trim());

            String[] idsCanciones = campos[8].split(",");
            for (String idTexto : idsCanciones) {
                if (!idTexto.trim().isEmpty()) {
                    Cancion cancion = buscarCancionPorId(catalogo, Integer.parseInt(idTexto.trim()));
                    if (cancion == null) {
                        throw new IOException("No se encontro la cancion del album: " + idTexto.trim());
                    }
                    album.agregarCancion(cancion);
                }
            }
            albumes.add(album);
        }
        return albumes;
    }

    /**
     * Carga entrevistas grabadas desde archivo.
     * Formato: id;titulo;duracion;descripcion;fecha;disponible;exclusivo;entrevistador;tematica;url
     */
    public List<EntrevistaGrabada> cargarEntrevistas(String ruta) throws IOException {
        List<EntrevistaGrabada> entrevistas = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            String[] campos = separar(linea);
            if (campos.length != 10) {
                throw new IOException("Formato invalido para entrevista.");
            }
            entrevistas.add(new EntrevistaGrabada(
                    Integer.parseInt(campos[0].trim()),
                    campos[1].trim(),
                    Integer.parseInt(campos[2].trim()),
                    campos[3].trim(),
                    campos[4].trim(),
                    Boolean.parseBoolean(campos[5].trim()),
                    Boolean.parseBoolean(campos[6].trim()),
                    campos[7].trim(),
                    campos[8].trim(),
                    campos[9].trim()));
        }
        return entrevistas;
    }

    /**
     * Carga playlists desde archivo (una por linea).
     * Formato: id;nombre;usuarioPropietarioId;contenidoIds
     */
    public List<Playlist> cargarPlaylists(String ruta, List<Usuario> usuarios,
                                          CatalogoMusical catalogo) throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        for (String linea : leerLineasValidas(ruta)) {
            String[] campos = separar(linea);
            if (campos.length != 4) {
                throw new IOException("El formato de playlist es invalido.");
            }

            Usuario propietario = buscarUsuarioPorId(usuarios, Integer.parseInt(campos[2].trim()));
            if (propietario == null) {
                throw new IOException("No se encontro el usuario propietario de la playlist.");
            }

            Playlist playlist = new Playlist(
                    Integer.parseInt(campos[0].trim()),
                    campos[1].trim(),
                    propietario);

            String[] idsContenido = campos[3].split(",");
            for (String idTexto : idsContenido) {
                if (!idTexto.trim().isEmpty()) {
                    Contenido contenido = catalogo.buscarPorId(Integer.parseInt(idTexto.trim()));
                    if (contenido != null) {
                        playlist.agregarContenido(contenido);
                    }
                }
            }
            propietario.agregarPlaylist(playlist);
            playlists.add(playlist);
        }
        return playlists;
    }

    /**
     * Guarda las canciones de un catalogo en un archivo de texto (sobrescribe).
     * Formato por linea: id;titulo;duracion;descripcion;fecha;disponible;exclusivo;genero;artistaId;letra
     */
    public void guardarCanciones(CatalogoMusical catalogo, String ruta) throws IOException {
        String contenidoArchivo = "# id;titulo;duracion;descripcion;fecha;disponible;exclusivo;genero;artistaId;letra"
                + System.lineSeparator();
        for (Contenido contenido : catalogo.getContenidos()) {
            if (contenido instanceof Cancion) {
                Cancion c = (Cancion) contenido;
                int artistaId = c.getArtista() != null ? c.getArtista().getId() : 0;
                contenidoArchivo = contenidoArchivo + c.getId() + SEPARADOR
                        + c.getTitulo() + SEPARADOR
                        + c.getDuracionSegundos() + SEPARADOR
                        + c.getDescripcion() + SEPARADOR
                        + c.getFechaPublicacion() + SEPARADOR
                        + c.isDisponible() + SEPARADOR
                        + c.isExclusivo() + SEPARADOR
                        + c.getGenero() + SEPARADOR
                        + artistaId + SEPARADOR
                        + c.getLetra()
                        + System.lineSeparator();
            }
        }
        escribir(ruta, contenidoArchivo);
    }

    /**
     * Guarda los comentarios de todo el catalogo (sobrescribe).
     * Formato por linea: tituloContenido;usuario;mensaje
     */
    public void guardarComentarios(CatalogoMusical catalogo, String ruta) throws IOException {
        String contenidoArchivo = "";
        for (Contenido contenido : catalogo.getContenidos()) {
            for (Comentario comentario : contenido.getComentarios()) {
                contenidoArchivo = contenidoArchivo + contenido.getTitulo() + SEPARADOR
                        + comentario.getAutor().getNombreUsuario() + SEPARADOR
                        + comentario.getMensaje()
                        + System.lineSeparator();
            }
        }
        escribir(ruta, contenidoArchivo);
    }

    /**
     * Recupera todos los comentarios guardados como texto legible.
     */
    public List<String> cargarComentarios(String ruta) throws IOException {
        return cargarComentarios(ruta, null);
    }

    /**
     * Recupera los comentarios guardados como texto legible.
     * Si tituloContenido no es nulo, filtra solo los del tema indicado.
     */
    public List<String> cargarComentarios(String ruta, String tituloContenido) throws IOException {
        List<String> comentarios = new ArrayList<>();
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(ruta));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(SEPARADOR);
                if (campos.length != 3) {
                    linea = lector.readLine();
                    continue;
                }
                String titulo = campos[0].trim();
                if (tituloContenido != null && !titulo.equalsIgnoreCase(tituloContenido.trim())) {
                    linea = lector.readLine();
                    continue;
                }
                comentarios.add("Tema: " + titulo + " | "
                        + campos[1].trim() + ": " + campos[2].trim());
                linea = lector.readLine();
            }
        } finally {
            if (lector != null) {
                lector.close();
            }
        }
        return comentarios;
    }

    private Cancion crearCancionFuente(String[] campos, List<Artista> artistas) throws IOException {
        if (campos.length != 10) {
            throw new IOException("Formato invalido para cancion.");
        }
        Artista artista = buscarArtistaPorId(artistas, Integer.parseInt(campos[8].trim()));
        if (artista == null) {
            throw new IOException("No se encontro el artista de la cancion: " + campos[8].trim());
        }
        return new Cancion(
                Integer.parseInt(campos[0].trim()),
                campos[1].trim(),
                Integer.parseInt(campos[2].trim()),
                campos[3].trim(),
                campos[4].trim(),
                Boolean.parseBoolean(campos[5].trim()),
                Boolean.parseBoolean(campos[6].trim()),
                campos[7].trim(),
                artista,
                campos[9].trim());
    }

    /** Omite lineas vacias y las que empiezan con '#' (encabezados de formato en los .txt). */
    private List<String> leerLineasValidas(String ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(ruta));
            String linea = lector.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty() && !linea.trim().startsWith("#")) {
                    lineas.add(linea);
                }
                linea = lector.readLine();
            }
        } finally {
            if (lector != null) {
                lector.close();
            }
        }
        return lineas;
    }

    private String[] separar(String linea) {
        return linea.split(SEPARADOR);
    }

    private Artista buscarArtistaPorId(List<Artista> artistas, int id) {
        for (Artista artista : artistas) {
            if (artista.getId() == id) {
                return artista;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorId(List<Usuario> usuarios, int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    private Cancion buscarCancionPorId(CatalogoMusical catalogo, int id) {
        Contenido contenido = catalogo.buscarPorId(id);
        if (contenido instanceof Cancion) {
            return (Cancion) contenido;
        }
        return null;
    }

    /** Escritura comun: crea la carpeta si no existe y sobrescribe el archivo. */
    private void escribir(String ruta, String contenido) throws IOException {
        Path archivo = Paths.get(ruta);
        Path carpeta = archivo.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
        Files.writeString(archivo, contenido);
    }
}
