package base;

import excepciones.ContenidoNoDisponibleException;
import excepciones.PlanInsuficienteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase abstracta comun que representa cualquier pieza de contenido de UADE Beats.
 * Implementa las interfaces Reproducible, Validable y Comparable, definiendo el
 * comportamiento base compartido por todas las clases concretas del catalogo.
 *
 * El control de acceso por plan se concentra aqui (Information Expert): Contenido
 * conoce si es exclusivo y delega en el Usuario la verificacion de su plan.
 */
public abstract class Contenido implements Reproducible, Validable, Comparable<Contenido> {

    protected final int id;
    protected String titulo;
    protected int duracionSegundos;
    protected String descripcion;
    protected String fechaPublicacion;
    protected boolean disponible;
    protected boolean exclusivo;

    protected final List<Comentario> comentarios;
    protected final List<Reaccion> reacciones;

    protected Contenido(int id, String titulo, int duracionSegundos, String descripcion,
                        String fechaPublicacion, boolean disponible, boolean exclusivo) {
        this.id = id;
        this.titulo = titulo;
        this.duracionSegundos = duracionSegundos;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.disponible = disponible;
        this.exclusivo = exclusivo;
        this.comentarios = new ArrayList<>();
        this.reacciones = new ArrayList<>();
    }

    /** Cada subclase define como mostrar su detalle (polimorfismo). */
    public abstract String mostrarDetalle();

    @Override
    public String reproducir() {
        return ">> Reproduciendo: " + titulo + " (" + formatearDuracion() + ")";
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
        return reproducir();
    }

    @Override
    public boolean esValido() {
        return titulo != null && !titulo.trim().isEmpty() && duracionSegundos > 0;
    }

    /** Orden natural del contenido: alfabetico por titulo (ignorando mayusculas). */
    @Override
    public int compareTo(Contenido otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }

    /**
     * Igualdad logica: dos contenidos son el mismo si comparten su identificador
     * de negocio (id). Respeta el contrato equals()/hashCode() exigido por las
     * colecciones basadas en hash (HashSet, HashMap).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Contenido)) {
            return false;
        }
        Contenido otro = (Contenido) obj;
        return this.id == otro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /** Devuelve la duracion en formato mm:ss. */
    protected String formatearDuracion() {
        int minutos = duracionSegundos / 60;
        int segundos = duracionSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    // Interacciones sociales (usadas por las subclases que implementan IInteractuable)

    protected void registrarReaccion(Reaccion reaccion) {
        if (reaccion != null) {
            reacciones.add(reaccion);
        }
    }

    protected void registrarComentario(Comentario comentario) {
        if (comentario != null) {
            comentarios.add(comentario);
        }
    }

    public List<Comentario> getComentarios() {
        return Collections.unmodifiableList(comentarios);
    }

    public List<Reaccion> getReacciones() {
        return Collections.unmodifiableList(reacciones);
    }

    public int getCantidadLikes() {
        int total = 0;
        for (Reaccion r : reacciones) {
            if (r.getTipo() == Reaccion.Tipo.LIKE) {
                total = total + 1;
            }
        }
        return total;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isExclusivo() {
        return exclusivo;
    }

    @Override
    public String toString() {
        return titulo + " [" + formatearDuracion() + "]" + (exclusivo ? " (exclusivo)" : "");
    }
}
