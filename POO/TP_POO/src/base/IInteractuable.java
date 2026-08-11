package base;

/**
 * Interfaz comun de UADE Beats para todo elemento con el que un usuario puede
 * interactuar socialmente.
 */
public interface IInteractuable {

    /** Registra un "me gusta" del usuario sobre el elemento. */
    String darLike(Usuario usuario);

    /** Registra un comentario del usuario sobre el elemento. */
    String comentar(Usuario usuario, String mensaje);

    /** Comparte el elemento dentro de la plataforma. */
    String compartir();
}
