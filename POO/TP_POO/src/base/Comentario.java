package base;

/**
 * Clase comun que representa un comentario realizado por un Usuario sobre un
 * contenido interactuable de UADE Beats.
 */
public class Comentario {

    private final Usuario autor;
    private final String mensaje;
    private final String fecha;

    public Comentario(Usuario autor, String mensaje) {
        if (autor == null) {
            throw new IllegalArgumentException("El autor del comentario no puede ser nulo.");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacio.");
        }
        this.autor = autor;
        this.mensaje = mensaje.trim();
        this.fecha = "Durante la ejecucion";
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return autor.getNombreUsuario() + ": " + mensaje;
    }
}
