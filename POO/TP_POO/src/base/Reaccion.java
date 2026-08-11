package base;

/**
 * Clase comun que representa una reaccion (por ejemplo, un "me gusta") de un
 * Usuario sobre un contenido interactuable de UADE Beats.
 */
public class Reaccion {

    /** Tipos de reaccion soportados por la plataforma. */
    public enum Tipo {
        LIKE, AMOR, SORPRESA
    }

    private final Usuario usuario;
    private final Tipo tipo;

    public Reaccion(Usuario usuario, Tipo tipo) {
        if (usuario == null || tipo == null) {
            throw new IllegalArgumentException("Usuario y tipo de reaccion son obligatorios.");
        }
        this.usuario = usuario;
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return usuario.getNombreUsuario() + " reacciono con " + tipo;
    }
}
