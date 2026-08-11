package excepciones;

/**
 * Se lanza cuando un usuario no es valido para la operacion solicitada
 * (por ejemplo, credenciales incorrectas o sesion no iniciada).
 */
public class UsuarioInvalidoException extends UadeBeatsException {

    public UsuarioInvalidoException(String mensaje) {
        super(mensaje);
    }
}
