package modulo;

import base.Contenido;
import base.Usuario;
import excepciones.ContenidoNoDisponibleException;
import excepciones.PlanInsuficienteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase concreta que representa un album musical en UADE Beats.
 * Hereda de Contenido (no es interactuable) y agrupa referencias a canciones.
 * Las canciones pueden existir tambien en el catalogo o en una playlist.
 * Su duracion se calcula a partir de las canciones que contiene.
 */
public class Album extends Contenido {

    private final List<Cancion> canciones;
    private int anioLanzamiento;
    private String discografica;

    public Album(int id, String titulo, String descripcion, String fechaPublicacion,
                 boolean disponible, boolean exclusivo, int anioLanzamiento, String discografica) {
        super(id, titulo, 0, descripcion, fechaPublicacion, disponible, exclusivo);
        this.canciones = new ArrayList<>();
        this.anioLanzamiento = anioLanzamiento;
        this.discografica = discografica;
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion != null && !canciones.contains(cancion)) {
            canciones.add(cancion);
            this.duracionSegundos = this.duracionSegundos + cancion.getDuracionSegundos();
        }
    }

    public boolean eliminarCancion(Cancion cancion) {
        boolean removida = canciones.remove(cancion);
        if (removida) {
            this.duracionSegundos = this.duracionSegundos - cancion.getDuracionSegundos();
        }
        return removida;
    }

    @Override
    public String reproducir(Usuario usuario)
            throws ContenidoNoDisponibleException, PlanInsuficienteException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (!disponible) {
            throw new ContenidoNoDisponibleException(
                    "El contenido '" + titulo + "' no esta disponible actualmente.");
        }
        if (!usuario.accederContenido(exclusivo)) {
            throw new PlanInsuficienteException(
                    "El plan " + usuario.getPlan() + " no permite reproducir contenido exclusivo. "
                            + "Se requiere PREMIUM o ARTIST_PASS.");
        }
        for (Cancion c : canciones) {
            if (!c.isDisponible()) {
                throw new ContenidoNoDisponibleException(
                        "El contenido '" + c.getTitulo() + "' no esta disponible actualmente.");
            }
            if (!usuario.accederContenido(c.isExclusivo())) {
                throw new PlanInsuficienteException(
                        "El plan " + usuario.getPlan() + " no permite reproducir contenido exclusivo. "
                                + "Se requiere PREMIUM o ARTIST_PASS.");
            }
        }
        return reproducir();
    }

    @Override
    public String reproducir() {
        String salto = System.lineSeparator();
        String salida = ">> Reproduciendo album: " + titulo + " (" + canciones.size() + " canciones)";
        for (Cancion c : canciones) {
            salida = salida + salto + c.reproducir();
        }
        return salida;
    }

    @Override
    public String mostrarDetalle() {
        String salto = System.lineSeparator();
        return "=== ALBUM ===" + salto
                + "Titulo      : " + titulo + salto
                + "Anio        : " + anioLanzamiento + salto
                + "Discografica: " + discografica + salto
                + "Canciones   : " + canciones.size() + salto
                + "Duracion    : " + formatearDuracion();
    }

    public List<Cancion> getCanciones() {
        return Collections.unmodifiableList(canciones);
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public String getDiscografica() {
        return discografica;
    }
}
