package base;

import excepciones.ContenidoNoDisponibleException;
import excepciones.PlanInsuficienteException;

/**
 * Interfaz comun para todo contenido que puede reproducirse en UADE Beats.
 * Separa la responsabilidad de "reproducir" del resto del modelo (ISP).
 */
public interface Reproducible {

    /** Reproduccion simple, sin control de acceso por usuario. */
    String reproducir();

    /**
     * Reproduccion con validacion del plan del usuario.
     * @param usuario usuario que solicita la reproduccion
     * @throws ContenidoNoDisponibleException si el contenido no esta disponible
     * @throws PlanInsuficienteException si el plan del usuario no alcanza
     */
    String reproducir(Usuario usuario)
            throws ContenidoNoDisponibleException, PlanInsuficienteException;
}
