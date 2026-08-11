package excepciones;

/**
 * Excepcion base de la plataforma UADE Beats. Todas las excepciones propias
 * del dominio heredan de esta clase, lo que permite capturarlas de forma
 * polimorfica cuando conviene.
 */
public class UadeBeatsException extends Exception {

    public UadeBeatsException(String mensaje) {
        super(mensaje);
    }
}
