package farmagest.modelo;

import java.time.LocalDate;

/**
 * TDA Lote
 * Representa un lote físico de un medicamento.
 * Unidad mínima de trazabilidad: número de lote único, fecha de vencimiento,
 * cantidad disponible y proveedor de origen.
 *
 * Implementa Comparable<Lote> comparando por fechaVencimiento (LocalDate).
 * Esto permite que PriorityQueue<Lote> mantenga el orden FEFO sin
 * comparador externo — el orden natural ascendente por fecha garantiza
 * que la raíz del Min-Heap siempre sea el lote que vence primero.
 *
 * Representación interna:
 *   - nroLote, fechaVencimiento, proveedor: final e inmutables.
 *   - cantidad: mutable, se decrementa con restarUnidades().
 */
public class Lote implements Comparable<Lote> {

    private final String nroLote;
    private final LocalDate fechaVencimiento;
    private int cantidad;
    private final String proveedor;

    /**
     * Precondición: nroLote único no nulo, fecha no nula, cantidad > 0, proveedor no nulo.
     */
    public Lote(String nroLote, LocalDate fechaVencimiento, int cantidad, String proveedor) {
        if (nroLote == null || nroLote.isBlank()) throw new IllegalArgumentException("El número de lote no puede ser nulo.");
        if (fechaVencimiento == null) throw new IllegalArgumentException("La fecha de vencimiento no puede ser nula.");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        if (proveedor == null || proveedor.isBlank()) throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        this.nroLote = nroLote;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
    }

    /** Retorna la fecha de vencimiento del lote. */
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }

    /** Retorna el número de identificación del lote. */
    public String getNroLote() { return nroLote; }

    /** Retorna las unidades disponibles en el lote. */
    public int getCantidad() { return cantidad; }

    /** Retorna el proveedor de origen del lote. */
    public String getProveedor() { return proveedor; }

    /**
     * Reduce el stock del lote en la cantidad indicada.
     * Precondición: cantidad > 0 y cantidad <= stock actual del lote.
     */
    public void restarUnidades(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a restar debe ser mayor a 0.");
        if (cantidad > this.cantidad) throw new IllegalStateException("No hay suficientes unidades en el lote.");
        this.cantidad -= cantidad;
    }

    /**
     * Retorna true si la fecha de vencimiento ya pasó respecto a la fecha actual.
     */
    public boolean estaVencido() {
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    /**
     * Compara dos lotes por fecha de vencimiento (orden natural ascendente).
     * Precondición: el otro lote no es nulo.
     * Usado por PriorityQueue (Min-Heap) para mantener el orden FEFO.
     */
    @Override
    public int compareTo(Lote otroLote) {
        return this.fechaVencimiento.compareTo(otroLote.fechaVencimiento);
    }

    @Override
    public String toString() {
        return String.format("Lote[nro=%s, vence=%s, cantidad=%d, proveedor=%s]",
                nroLote, fechaVencimiento, cantidad, proveedor);
    }
}
