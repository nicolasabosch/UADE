package excepciones;

/**
 * Se lanza cuando se intenta reproducir un contenido marcado como no disponible.
 */
public class ContenidoNoDisponibleException extends UadeBeatsException {

    public ContenidoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
