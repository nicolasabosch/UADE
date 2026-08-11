package excepciones;

/**
 * Se lanza cuando el plan de suscripcion del usuario no alcanza para acceder
 * a un contenido exclusivo.
 */
public class PlanInsuficienteException extends UadeBeatsException {

    public PlanInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
