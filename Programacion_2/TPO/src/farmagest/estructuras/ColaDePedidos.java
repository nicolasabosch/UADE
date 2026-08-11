package farmagest.estructuras;

import farmagest.modelo.Pedido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * TDA ColaDePedidos
 * Gestiona los pedidos de reposición generados hacia los proveedores
 * respetando el orden de llegada (FIFO).
 *
 * Representación interna: LinkedList<Pedido> utilizada como Queue.
 *   - offer (encolar):          O(1) — inserción al final.
 *   - poll (desencolar):        O(1) — extracción del frente.
 *   - peek (consultar frente):  O(1).
 *
 * Alternativa descartada: PriorityQueue<Pedido> con prioridad por urgencia.
 *   El enunciado no define criterios de urgencia diferenciada entre pedidos.
 *   La equidad FIFO modela con precisión el comportamiento real de una farmacia.
 *   Si en una versión futura se incorporaran niveles de urgencia, la migración
 *   a PriorityQueue es directa dado que ColaDePedidos aísla la implementación.
 */
public class ColaDePedidos {

    private final Queue<Pedido> cola;

    public ColaDePedidos() {
        this.cola = new LinkedList<>();
    }

    /**
     * Agrega el pedido al final de la cola.
     * Precondición: pedido no nulo.
     * Complejidad: O(1).
     */
    public void encolarPedido(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("El pedido no puede ser nulo.");
        cola.offer(pedido);
    }

    /**
     * Extrae y retorna el pedido más antiguo de la cola.
     * Precondición: la cola no está vacía.
     * Complejidad: O(1).
     */
    public Pedido procesarPedido() {
        if (estaVacia()) throw new IllegalStateException("No hay pedidos en la cola para procesar.");
        return cola.poll();
    }

    /**
     * Consulta el próximo pedido sin extraerlo.
     * Precondición: la cola no está vacía.
     * Complejidad: O(1).
     */
    public Pedido verProximoPedido() {
        if (estaVacia()) throw new IllegalStateException("No hay pedidos en la cola.");
        return cola.peek();
    }

    /**
     * Retorna true si no hay pedidos en espera.
     * Complejidad: O(1).
     */
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    /**
     * Retorna la cantidad de pedidos pendientes.
     * Complejidad: O(1).
     */
    public int cantidadPendientes() {
        return cola.size();
    }

    /**
     * Retorna una vista de todos los pedidos en cola sin modificarla.
     * Complejidad: O(n) — copia defensiva.
     */
    public List<Pedido> listarPedidos() {
        return new ArrayList<>(cola);
    }

    @Override
    public String toString() {
        return String.format("ColaDePedidos[pendientes=%d]", cola.size());
    }
}
