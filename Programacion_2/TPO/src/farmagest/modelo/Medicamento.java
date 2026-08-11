package farmagest.modelo;

/**
 * TDA Medicamento
 * Representa un medicamento del catálogo general de FarmaGest.
 * Encapsula la identidad y metadatos del producto farmacéutico.
 * No contiene información de stock; esa responsabilidad recae en el TDA Inventario.
 *
 * Representación interna: atributos final e inmutables (POJO).
 * equals() y hashCode() basados en el código único, garantizando
 * comportamiento correcto como clave en HashMaps.
 */
public class Medicamento {

    private final String codigo;
    private final String nombre;
    private final String droga;
    private final String presentacion;

    /**
     * Precondición: codigo único no nulo, nombre no nulo, droga no nula.
     */
    public Medicamento(String codigo, String nombre, String droga, String presentacion) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        if (droga == null || droga.isBlank()) throw new IllegalArgumentException("La droga no puede ser nula o vacía.");
        this.codigo = codigo;
        this.nombre = nombre;
        this.droga = droga;
        this.presentacion = (presentacion != null) ? presentacion : "";
    }

    /** Retorna el código identificador único del medicamento. */
    public String getCodigo() { return codigo; }

    /** Retorna el nombre comercial del medicamento. */
    public String getNombre() { return nombre; }

    /** Retorna el principio activo (droga genérica). */
    public String getDroga() { return droga; }

    /** Retorna la forma farmacéutica y concentración. */
    public String getPresentacion() { return presentacion; }

    /**
     * Compara dos medicamentos por su código único.
     * Precondición: el parámetro no es nulo.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Medicamento)) return false;
        return this.codigo.equals(((Medicamento) obj).codigo);
    }

    @Override
    public int hashCode() { return codigo.hashCode(); }

    @Override
    public String toString() {
        return String.format("Medicamento[codigo=%s, nombre=%s, droga=%s, presentacion=%s]",
                codigo, nombre, droga, presentacion);
    }
}
