package farmagest.estructuras;

import farmagest.modelo.Lote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * TDA ColaDeLotes
 * Mantiene los lotes de un medicamento en una sucursal ordenados
 * por fecha de vencimiento ascendente. Es el núcleo de la política
 * FEFO (First Expired, First Out) del sistema.
 *
 * Representación interna:
 *   - heap: PriorityQueue<Lote> — Min-Heap binario implementado
 *     internamente por Java mediante un array. Lote implementa
 *     Comparable<Lote> por fechaVencimiento, por lo que no se
 *     necesita comparador externo.
 *       peek (consultar mínimo): O(1)
 *       offer (insertar):       O(log n) via sift-up
 *       poll (extraer mínimo):  O(log n) via sift-down
 *
 *   - stockTotal: int — acumulador que mantiene la suma de unidades
 *     disponibles en todos los lotes activos. Se incrementa en
 *     agregar() y se decrementa en despachar() y eliminarVencidos().
 *     Permite que hayStock() y cantidadTotal() sean O(1) en lugar de
 *     O(n) (que requeriría recorrer el heap sumando cantidades).
 */
public class ColaDeLotes {

    private final PriorityQueue<Lote> heap;
    private int stockTotal;

    public ColaDeLotes() {
        this.heap = new PriorityQueue<>();
        this.stockTotal = 0;
    }

    /**
     * Inserta un lote en el heap respetando el orden FEFO.
     * El acumulador stockTotal se incrementa con la cantidad del lote.
     * Precondición: lote no nulo.
     * Complejidad: O(log n) — sift-up en el heap.
     */
    public void agregar(Lote lote) {
        if (lote == null) throw new IllegalArgumentException("El lote no puede ser nulo.");
        heap.offer(lote);
        stockTotal += lote.getCantidad();
    }

    /**
     * Retorna el lote con fecha de vencimiento más próxima sin extraerlo.
     * Precondición: la cola no está vacía.
     * Complejidad: O(1) — acceso directo a la raíz del heap.
     */
    public Lote proximoAVencer() {
        if (heap.isEmpty()) throw new IllegalStateException("La cola de lotes está vacía.");
        return heap.peek();
    }

    /**
     * Despacha 'cantidad' unidades aplicando política FEFO.
     * Consume el lote raíz del heap (el de menor fecha de vencimiento).
     * Si el lote se agota, lo extrae (poll O(log n)) y continúa con el siguiente.
     * Descuenta la cantidad total despachada del acumulador stockTotal.
     *
     * Precondición: hayStock() == true y cantidad <= stockTotal.
     * Complejidad: O(k log n), donde k = cantidad de lotes agotados
     *   durante el despacho. Caso más común (despacho parcial de un
     *   lote): O(log n).
     */
    public void despachar(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a despachar debe ser mayor a 0.");
        if (!hayStock()) throw new IllegalStateException("No hay stock disponible para despachar.");
        if (cantidad > stockTotal) throw new IllegalStateException(
                "Stock insuficiente: solicitado=" + cantidad + ", disponible=" + stockTotal);

        int restante = cantidad;
        while (restante > 0) {
            Lote actual = heap.peek();
            if (actual == null) break;

            if (actual.getCantidad() <= restante) {
                // Lote agotado completamente: extraer del heap — O(log n)
                restante -= actual.getCantidad();
                heap.poll();
            } else {
                // Lote con stock suficiente: descontar unidades — O(1)
                actual.restarUnidades(restante);
                restante = 0;
            }
        }
        stockTotal -= cantidad;
    }

    /**
     * Retorna true si hay al menos una unidad disponible.
     * Complejidad: O(1) — consulta directa al acumulador.
     */
    public boolean hayStock() {
        return stockTotal > 0;
    }

    /**
     * Retorna la cantidad total de unidades disponibles en todos los lotes.
     * Complejidad: O(1) — consulta directa al acumulador.
     */
    public int cantidadTotal() {
        return stockTotal;
    }

    /**
     * Elimina todos los lotes cuya fecha de vencimiento ya pasó.
     * Recorre el heap completo, separa los vencidos, y reconstruye
     * el heap solo con los lotes vigentes.
     * Descuenta del acumulador la cantidad de los lotes eliminados.
     * Complejidad: O(n log n) — reconstrucción del heap.
     */
    public void eliminarVencidos() {
        List<Lote> noVencidos = new ArrayList<>();
        int stockEliminado = 0;

        while (!heap.isEmpty()) {
            Lote lote = heap.poll();
            if (!lote.estaVencido()) {
                noVencidos.add(lote);
            } else {
                stockEliminado += lote.getCantidad();
            }
        }

        for (Lote lote : noVencidos) {
            heap.offer(lote);
        }
        stockTotal -= stockEliminado;
    }

    /**
     * Retorna la cantidad de lotes activos en la cola.
     * Complejidad: O(1).
     */
    public int size() {
        return heap.size();
    }

    /**
     * Retorna true si la cola de lotes está vacía.
     */
    public boolean estaVacia() {
        return heap.isEmpty();
    }

    /**
     * Retorna una lista ordenada de los lotes actuales (para visualización).
     * No modifica la cola.
     */
    public List<Lote> getLotesOrdenados() {
        List<Lote> snapshot = new ArrayList<>(heap);
        Collections.sort(snapshot);
        return snapshot;
    }

    @Override
    public String toString() {
        return String.format("ColaDeLotes[lotes=%d, stockTotal=%d, proximoVence=%s]",
                heap.size(), stockTotal,
                heap.isEmpty() ? "N/A" : heap.peek().getFechaVencimiento());
    }
}
