package base;

/**
 * Interfaz comun para entidades que pueden validar la consistencia de su
 * propio estado interno (reglas de negocio basicas).
 */
public interface Validable {

    /**
     * Verifica que el estado del objeto cumpla sus reglas minimas.
     * @return true si el objeto es valido
     */
    boolean esValido();
}
