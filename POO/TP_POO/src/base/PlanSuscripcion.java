package base;

/**
 * Enumeracion comun de UADE Beats que representa los planes de suscripcion.
 * Respeta los valores definidos en la consigna: FREE, PREMIUM y ARTIST_PASS.
 * Cada plan encapsula sus propios privilegios mediante atributos y metodos,
 * evitando condicionales dispersos por el sistema (Information Expert).
 */
public enum PlanSuscripcion {

    FREE("Gratuito", false, false),
    PREMIUM("Premium", true, false),
    ARTIST_PASS("Artist Pass", true, true);

    private final String descripcion;
    private final boolean permiteContenidoExclusivo;
    private final boolean permiteEntrevistas;

    PlanSuscripcion(String descripcion, boolean permiteContenidoExclusivo, boolean permiteEntrevistas) {
        this.descripcion = descripcion;
        this.permiteContenidoExclusivo = permiteContenidoExclusivo;
        this.permiteEntrevistas = permiteEntrevistas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /** Indica si el plan habilita reproducir contenido marcado como exclusivo/premium. */
    public boolean permiteContenidoExclusivo() {
        return permiteContenidoExclusivo;
    }

    /** Indica si el plan habilita acceder a entrevistas grabadas. */
    public boolean permiteEntrevistas() {
        return permiteEntrevistas;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
