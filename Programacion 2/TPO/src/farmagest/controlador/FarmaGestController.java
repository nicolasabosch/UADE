package farmagest.controlador;

import farmagest.estructuras.*;
import farmagest.modelo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * FarmaGestController — Motor central de FarmaGest
 *
 * Único punto de acceso externo al sistema. Coordina las operaciones
 * entre los distintos TDAs aplicando las reglas de negocio de FarmaGest.
 * Ningún TDA contiene lógica de dominio propia.
 *
 * Referencias que mantiene:
 *   - CatalogoMedicamentos: catálogo global de medicamentos.
 *   - Inventario: gestión de stock por sucursal y medicamento.
 *   - ColaDePedidos: cola global de reposiciones (FIFO).
 *   - RedDeSustitutos: grafo de equivalencias terapéuticas.
 *
 * Métodos públicos (alineados con el documento de diseño):
 *   Gestión:      registrarMedicamento, registrarSucursal, configurarUmbral, agregarSustitucion
 *   Stock:        agregarLote, despacharMedicamento, consultarStock, hayStock
 *   Sustitutos:   buscarSustituto
 *   Pedidos:      procesarPedido, pedidosPendientes, listarPedidosPendientes
 *   Batch:        purgarVencidos
 *   Consultas:    buscarMedicamento, proximoAVencer
 */
public class FarmaGestController {

    private final CatalogoMedicamentos catalogo;
    private final Inventario inventario;
    private final ColaDePedidos colaDePedidos;
    private final RedDeSustitutos redDeSustitutos;

    // Generador de IDs únicos y auto-incrementales para pedidos
    private int contadorPedidos = 1;

    public FarmaGestController() {
        this.catalogo = new CatalogoMedicamentos();
        this.inventario = new Inventario();
        this.colaDePedidos = new ColaDePedidos();
        this.redDeSustitutos = new RedDeSustitutos();
    }

    // ─────────────────────────────────────────────────────────────
    // GESTIÓN DE MEDICAMENTOS Y SUCURSALES
    // ─────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo medicamento en el catálogo y en la red de sustitutos.
     * Precondición: código único no nulo.
     * Complejidad: O(1).
     */
    public void registrarMedicamento(String codigo, String nombre, String droga, String presentacion) {
        Medicamento med = new Medicamento(codigo, nombre, droga, presentacion);
        catalogo.registrar(med);
        redDeSustitutos.agregarNodo(codigo);
    }

    /**
     * Registra una nueva sucursal en el inventario.
     * Complejidad: O(1).
     */
    public void registrarSucursal(String sucId) {
        inventario.registrarSucursal(sucId);
    }

    /**
     * Configura el umbral mínimo de stock para un par (sucursal, medicamento).
     * Cuando el stock cae a este nivel o por debajo, se genera un pedido automático.
     * Complejidad: O(1).
     */
    public void configurarUmbral(String sucId, String medId, int umbralMinimo) {
        inventario.configurarUmbral(sucId, medId, umbralMinimo);
    }

    /**
     * Registra la relación de sustitución terapéutica entre dos medicamentos.
     * La relación es simétrica: si A sustituye a B, B sustituye a A.
     * Precondición: ambos medicamentos deben estar registrados en el catálogo.
     * Complejidad: O(1).
     */
    public void agregarSustitucion(String medId1, String medId2) {
        if (!catalogo.contiene(medId1) || !catalogo.contiene(medId2)) {
            throw new IllegalArgumentException("Ambos medicamentos deben estar en el catálogo.");
        }
        redDeSustitutos.agregarArista(medId1, medId2);
    }

    // ─────────────────────────────────────────────────────────────
    // GESTIÓN DE STOCK
    // ─────────────────────────────────────────────────────────────

    /**
     * Ingresa un nuevo lote de un medicamento en una sucursal.
     *
     * Flujo:
     *   1. Valida que el medicamento existe en el catálogo: O(1).
     *   2. Inserta el lote en la ColaDeLotes del par (sucId, medId): O(log n).
     *      Si es el primer lote del par, el Inventario crea su ColaDeLotes.
     *
     * Complejidad total: O(log n).
     */
    public void agregarLote(String sucId, String medId, String nroLote,
                            LocalDate fechaVencimiento, int cantidad, String proveedor) {
        if (!catalogo.contiene(medId)) {
            throw new IllegalArgumentException("Medicamento no registrado en el catálogo: " + medId);
        }
        Lote lote = new Lote(nroLote, fechaVencimiento, cantidad, proveedor);
        inventario.agregarLote(sucId, medId, lote);
    }

    /**
     * Despacha 'cantidad' unidades de un medicamento en una sucursal.
     * Aplica la política FEFO automáticamente a través del Min-Heap.
     * Si el stock resultante cae por debajo del umbral, genera un pedido automático.
     *
     * Flujo:
     *   1. Valida el medicamento en el catálogo: O(1).
     *   2. Verifica hayStock() via acumulador: O(1).
     *   3. Verifica stock suficiente via consultarStock(): O(1).
     *   4. Ejecuta despacho FEFO en ColaDeLotes: O(k log n).
     *   5. Si stock <= umbral: encola pedido automático: O(1).
     *
     * Complejidad total: O(k log n), donde k = lotes agotados durante el despacho.
     *
     * @return true si el despacho fue exitoso, false si no hay stock suficiente.
     */
    public boolean despacharMedicamento(String sucId, String medId, int cantidad) {
        if (!catalogo.contiene(medId)) {
            throw new IllegalArgumentException("Medicamento no registrado en el catálogo: " + medId);
        }
        if (!inventario.hayStock(sucId, medId)) {
            System.out.println("[ADVERTENCIA] Sin stock para " + medId + " en " + sucId);
            return false;
        }
        if (inventario.consultarStock(sucId, medId) < cantidad) {
            System.out.println("[ADVERTENCIA] Stock insuficiente para " + medId + " en " + sucId);
            return false;
        }

        inventario.despachar(sucId, medId, cantidad);

        if (inventario.umbralBajo(sucId, medId)) {
            generarPedidoAutomatico(sucId, medId);
        }

        return true;
    }

    /**
     * Consulta el stock disponible de un medicamento en una sucursal.
     * Complejidad: O(1) — acumulador stockTotal.
     */
    public int consultarStock(String sucId, String medId) {
        return inventario.consultarStock(sucId, medId);
    }

    /**
     * Verifica si hay stock disponible de un medicamento en una sucursal.
     * Complejidad: O(1) — acumulador stockTotal.
     */
    public boolean hayStock(String sucId, String medId) {
        return inventario.hayStock(sucId, medId);
    }

    // ─────────────────────────────────────────────────────────────
    // SUSTITUTOS TERAPÉUTICOS
    // ─────────────────────────────────────────────────────────────

    /**
     * Busca el sustituto terapéutico disponible más cercano para un
     * medicamento sin stock en una sucursal.
     *
     * Flujo:
     *   1. Verifica ausencia de stock: O(1) via acumulador.
     *   2. Ejecuta BFS en RedDeSustitutos, excluyendo el nodo origen.
     *   3. Para cada nodo visitado distinto del origen: hayStock() O(1).
     *   4. Retorna el Medicamento del primer sustituto encontrado, o null.
     *
     * Complejidad total: O(V + E).
     */
    public Medicamento buscarSustituto(String sucId, String medId) {
        if (!catalogo.contiene(medId)) {
            throw new IllegalArgumentException("Medicamento no registrado: " + medId);
        }
        if (inventario.hayStock(sucId, medId)) {
            System.out.println("[INFO] " + medId + " tiene stock. No se busca sustituto.");
            return catalogo.buscarPorCodigo(medId);
        }

        String sustitutoId = redDeSustitutos.buscarSustitutoDisponible(medId, inventario, sucId);
        if (sustitutoId == null) {
            System.out.println("[INFO] No se encontró sustituto para " + medId + " en " + sucId);
            return null;
        }
        return catalogo.buscarPorCodigo(sustitutoId);
    }

    // ─────────────────────────────────────────────────────────────
    // GESTIÓN DE PEDIDOS
    // ─────────────────────────────────────────────────────────────

    /**
     * Genera un pedido de reposición automático al detectar stock bajo umbral.
     * Encola el pedido en ColaDePedidos: O(1).
     */
    private void generarPedidoAutomatico(String sucId, String medId) {
        String idPedido = "PED-" + contadorPedidos;
        contadorPedidos++;
        int cantidadReposicion = 100;
        Pedido pedido = new Pedido(idPedido, sucId, medId, cantidadReposicion, "PROVEEDOR_DEFAULT");
        colaDePedidos.encolarPedido(pedido);
        System.out.println("[PEDIDO GENERADO] " + pedido);
    }

    /**
     * Genera un pedido de reposición manual.
     * Complejidad: O(1).
     */
    public void generarPedido(String sucId, String medId, int cantidad, String proveedor) {
        if (!catalogo.contiene(medId)) {
            throw new IllegalArgumentException("Medicamento no registrado: " + medId);
        }
        String idPedido = "PED-" + contadorPedidos;
        contadorPedidos++;
        Pedido pedido = new Pedido(idPedido, sucId, medId, cantidad, proveedor);
        colaDePedidos.encolarPedido(pedido);
    }

    /**
     * Procesa el próximo pedido pendiente en la cola FIFO.
     * Crea el lote correspondiente e ingresa el stock al inventario.
     *
     * Flujo:
     *   1. Extrae el pedido de ColaDePedidos (poll FIFO): O(1).
     *   2. Crea un Lote con los datos del pedido.
     *   3. Ingresa el lote al Inventario: O(log n).
     *
     * Complejidad total: O(log n).
     *
     * @return el Pedido procesado, o null si la cola está vacía.
     */
    public Pedido procesarPedido(String nroLote, LocalDate fechaVencimiento) {
        if (colaDePedidos.estaVacia()) {
            System.out.println("[INFO] No hay pedidos pendientes en la cola.");
            return null;
        }
        Pedido pedido = colaDePedidos.procesarPedido();
        Lote lote = new Lote(nroLote, fechaVencimiento, pedido.getCantidadSolicitada(), pedido.getProveedor());
        inventario.agregarLote(pedido.getSucursalId(), pedido.getMedicamentoId(), lote);
        System.out.println("[PEDIDO PROCESADO] " + pedido + " -> lote ingresado: " + nroLote);
        return pedido;
    }

    /**
     * Retorna la cantidad de pedidos pendientes en la cola.
     * Complejidad: O(1).
     */
    public int pedidosPendientes() {
        return colaDePedidos.cantidadPendientes();
    }

    /**
     * Retorna la lista de pedidos pendientes sin modificar la cola.
     * Complejidad: O(n).
     */
    public List<Pedido> listarPedidosPendientes() {
        return colaDePedidos.listarPedidos();
    }

    // ─────────────────────────────────────────────────────────────
    // OPERACIONES BATCH
    // ─────────────────────────────────────────────────────────────

    /**
     * Purga los lotes vencidos de todas las sucursales y medicamentos.
     * Complejidad: O(S × M × n log n)
     *   S = sucursales, M = medicamentos por sucursal, n = lotes por ColaDeLotes.
     */
    public void purgarVencidos() {
        for (String sucId : inventario.getSucursales()) {
            Map<String, ColaDeLotes> medSucursal = inventario.getMedicamentosDeSucursal(sucId);
            for (Map.Entry<String, ColaDeLotes> entry : medSucursal.entrySet()) {
                entry.getValue().eliminarVencidos();
            }
        }
        System.out.println("[BATCH] Purga de lotes vencidos completada.");
    }

    // ─────────────────────────────────────────────────────────────
    // CONSULTAS
    // ─────────────────────────────────────────────────────────────

    /**
     * Busca un medicamento en el catálogo por su código.
     * Complejidad: O(1).
     */
    public Medicamento buscarMedicamento(String codigo) {
        return catalogo.buscarPorCodigo(codigo);
    }

    /**
     * Retorna el lote más próximo a vencer de un medicamento en una sucursal.
     * Complejidad: O(1) — peek del Min-Heap.
     */
    public Lote proximoAVencer(String sucId, String medId) {
        return inventario.obtenerColaDeLotes(sucId, medId).proximoAVencer();
    }
}
