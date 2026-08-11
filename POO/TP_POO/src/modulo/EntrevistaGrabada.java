package modulo;

import base.Usuario;
import excepciones.ContenidoNoDisponibleException;
import excepciones.PlanInsuficienteException;

/**
 * Clase concreta que representa una entrevista grabada (video exclusivo) en
 * UADE Beats. Hereda de ContenidoAudiovisual (interactuable como un videoclip).
 * Solo usuarios con plan ARTIST_PASS pueden reproducirla e interactuar con ella.
 */
public class EntrevistaGrabada extends ContenidoAudiovisual {

    private String entrevistador;
    private String tematica;
    private String urlVideo;

    public EntrevistaGrabada(int id, String titulo, int duracionSegundos, String descripcion,
                             String fechaPublicacion, boolean disponible, boolean exclusivo,
                             String entrevistador, String tematica, String urlVideo) {
        super(id, titulo, duracionSegundos, descripcion, fechaPublicacion, disponible, exclusivo);
        this.entrevistador = entrevistador;
        this.tematica = tematica;
        this.urlVideo = urlVideo;
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
        if (!usuario.accederEntrevista()) {
            throw new PlanInsuficienteException(
                    "El plan " + usuario.getPlan() + " no permite acceder a entrevistas. "
                            + "Se requiere ARTIST_PASS.");
        }
        return reproducir();
    }

    @Override
    public String darLike(Usuario usuario) {
        if (!usuario.accederEntrevista()) {
            return "   ACCESO DENEGADO: el plan " + usuario.getPlan()
                    + " no permite interactuar con entrevistas.";
        }
        return super.darLike(usuario);
    }

    @Override
    public String comentar(Usuario usuario, String mensaje) {
        if (!usuario.accederEntrevista()) {
            return "   ACCESO DENEGADO: el plan " + usuario.getPlan()
                    + " no permite interactuar con entrevistas.";
        }
        return super.comentar(usuario, mensaje);
    }

    @Override
    public String reproducir() {
        return ">> Reproduciendo entrevista: " + titulo
                + " (entrevistador: " + entrevistador + ")";
    }

    @Override
    public String mostrarDetalle() {
        String salto = System.lineSeparator();
        return "=== ENTREVISTA GRABADA ===" + salto
                + "Titulo       : " + titulo + salto
                + "Entrevistador: " + entrevistador + salto
                + "Tematica     : " + tematica + salto
                + "Duracion     : " + formatearDuracion() + salto
                + "Exclusivo    : " + (exclusivo ? "Si" : "No") + salto
                + "Likes        : " + getCantidadLikes();
    }

    public String getEntrevistador() {
        return entrevistador;
    }

    public String getTematica() {
        return tematica;
    }

    public String getUrlVideo() {
        return urlVideo;
    }
}
