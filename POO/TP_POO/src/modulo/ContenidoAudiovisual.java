package modulo;

import base.Comentario;
import base.Contenido;
import base.IInteractuable;
import base.Reaccion;
import base.Usuario;

/**
 * Clase abstracta intermedia del Modulo Musica y Videos.
 * Extiende Contenido y agrega el comportamiento social (IInteractuable) que
 * comparten las piezas con las que el usuario puede interactuar directamente:
 * canciones, videoclips y entrevistas grabadas. Album NO hereda de esta clase
 * porque agrupa canciones y no es interactuable en forma directa.
 */
public abstract class ContenidoAudiovisual extends Contenido implements IInteractuable {

    protected ContenidoAudiovisual(int id, String titulo, int duracionSegundos, String descripcion,
                                   String fechaPublicacion, boolean disponible, boolean exclusivo) {
        super(id, titulo, duracionSegundos, descripcion, fechaPublicacion, disponible, exclusivo);
    }

    @Override
    public String darLike(Usuario usuario) {
        for (Reaccion r : reacciones) {
            if (r.getTipo() == Reaccion.Tipo.LIKE && r.getUsuario().getId() == usuario.getId()) {
                return "   " + usuario.getNombreUsuario() + " ya dio like a '" + titulo + "'.";
            }
        }
        registrarReaccion(new Reaccion(usuario, Reaccion.Tipo.LIKE));
        return "   " + usuario.getNombreUsuario() + " dio like a '" + titulo
                + "'. Total likes: " + getCantidadLikes();
    }

    @Override
    public String comentar(Usuario usuario, String mensaje) {
        Comentario comentario = new Comentario(usuario, mensaje);
        registrarComentario(comentario);
        return "   Comentario en '" + titulo + "' -> " + comentario;
    }

    @Override
    public String compartir() {
        return "   '" + titulo + "' fue compartido en UADE Beats.";
    }
}
