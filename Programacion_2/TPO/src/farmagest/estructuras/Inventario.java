package farmagest.estructuras;

import farmagest.modelo.Lote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TDA Inventario
 * Gestiona el stock de todos los medicamentos en todas las sucursales.
 *
 * Representación interna: HashMap anidado
 *   Map<sucursalId, Map<medicamentoId, ColaDeLotes>>
 *
 *   - Acceso O(1) al par (sucursal, medicamento): dos lookups en HashMap.
 *   - Al ingresar el primer lote de un par nuevo, se crea su ColaDeLotes
 *     consultando antes su existencia (patrón de diccionario simple).
 *
 * Alternativa descartada: HashMap con clave compuesta "sucId_medId".
 *   Es frágil (requiere contrato de formato y escapado), dificulta la
 *   iteración por sucursal y genera acoplamiento implícito. El anidado
 *   es semánticamente más claro e igualmente eficiente.
 *
 * HashMap paralelo para umbrales mínimos:
 *   Map<sucursalId, Map<medicamentoId, Integer>>
 *   Cuando el stockTotal cae al umbral o por debajo, el controlador
 *   genera automáticamente un pedido de reposición.
 */
public class Inventario {

    private final Map<String, Map<String, ColaDeLotes>> stock;
    private final Map<String, Map<String, Integer>> umbrales;

    public Inventario() {
        this.stock = new HashMap<>();
        this.umbrales = new HashMap<>();
    }

    /**
     * Registra una nueva sucursal en la estructura principal.
     * Precondición: sucId único no nulo.
     * Complejidad: O(1).
     */
    public void registrarSucursal(String sucId) {
        if (sucId == null || sucId.isBlank()) throw new IllegalArgumentException("El ID de sucursal no puede ser nulo.");
        stock.putIfAbsent(sucId, new HashMap<>());
        umbrales.putIfAbsent(sucId, new HashMap<>());
    }

    /**
     * Establece el umbral mínimo de stock para un par (sucursal, medicamento).
     * Precondición: umbral >= 0.
     * Complejidad: O(1).
     */
    public void configurarUmbral(String sucId, String medId, int umbralMinimo) {
        validarSucursal(sucId);
        if (umbralMinimo < 0) throw new IllegalArgumentException("El umbral no puede ser negativo.");
        umbrales.get(sucId).put(medId, umbralMinimo);
    }

    /**
     * Inserta un lote en la ColaDeLotes del par (sucId, medId).
     * Si es el primer lote del par, se crea la ColaDeLotes asociada
     * (patrón de diccionario: consultar existencia y, si no existe, asociar).
     * Precondición: sucId y medId válidos, lote no nulo.
     * Complejidad: O(log n) — inserción en la cola con prioridad.
     */
    public void agregarLote(String sucId, String medId, Lote lote) {
        validarSucursal(sucId);
        if (medId == null) throw new IllegalArgumentException("El ID de medicamento no puede ser nulo.");
        if (lote == null) throw new IllegalArgumentException("El lote no puede ser nulo.");
        Map<String, ColaDeLotes> medsDeSucursal = stock.get(sucId);
        if (!medsDeSucursal.containsKey(medId)) {
            medsDeSucursal.put(medId, new ColaDeLotes());
        }
        medsDeSucursal.get(medId).agregar(lote);
    }

    /**
     * Retorna la ColaDeLotes del medicamento en la sucursal indicada.
     * Precondición: sucId y medId registrados.
     * Complejidad: O(1) — dos lookups en HashMap.
     */
    public ColaDeLotes obtenerColaDeLotes(String sucId, String medId) {
        validarSucursal(sucId);
        ColaDeLotes cola = stock.get(sucId).get(medId);
        if (cola == null) throw new IllegalArgumentException(
                "No hay lotes registrados para " + medId + " en sucursal " + sucId);
        return cola;
    }

    /**
     * Retorna el stockTotal del medicamento en la sucursal — O(1).
     * Precondición: sucId y medId registrados.
     */
    public int consultarStock(String sucId, String medId) {
        validarSucursal(sucId);
        Map<String, ColaDeLotes> stockSucursal = stock.get(sucId);
        if (!stockSucursal.containsKey(medId)) return 0;
        return stockSucursal.get(medId).cantidadTotal();
    }

    /**
     * Retorna true si hay stock disponible para el medicamento en la sucursal.
     * Complejidad: O(1) — acumulador stockTotal.
     */
    public boolean hayStock(String sucId, String medId) {
        validarSucursal(sucId);
        Map<String, ColaDeLotes> stockSucursal = stock.get(sucId);
        if (!stockSucursal.containsKey(medId)) return false;
        return stockSucursal.get(medId).hayStock();
    }

    /**
     * Despacha 'cantidad' unidades del medicamento en la sucursal aplicando FEFO.
     * Precondición: sucId y medId registrados, cantidad <= stock disponible.
     * Complejidad: O(k log n).
     */
    public void despachar(String sucId, String medId, int cantidad) {
        obtenerColaDeLotes(sucId, medId).despachar(cantidad);
    }

    /**
     * Verifica si el stock actual está por debajo del umbral mínimo configurado.
     * Si no hay umbral configurado para el par, retorna false.
     * Complejidad: O(1).
     */
    public boolean umbralBajo(String sucId, String medId) {
        validarSucursal(sucId);
        Map<String, Integer> umbralesSucursal = umbrales.get(sucId);
        if (!umbralesSucursal.containsKey(medId)) return false;
        int umbral = umbralesSucursal.get(medId);
        return consultarStock(sucId, medId) <= umbral;
    }

    /**
     * Retorna el conjunto de IDs de sucursales registradas.
     * Complejidad: O(1).
     */
    public Set<String> getSucursales() {
        return stock.keySet();
    }

    /**
     * Retorna el mapa de ColaDeLotes de una sucursal completa.
     * Útil para operaciones batch como purgarVencidos().
     * Complejidad: O(1).
     */
    public Map<String, ColaDeLotes> getMedicamentosDeSucursal(String sucId) {
        validarSucursal(sucId);
        return stock.get(sucId);
    }

    private void validarSucursal(String sucId) {
        if (sucId == null || !stock.containsKey(sucId)) {
            throw new IllegalArgumentException("Sucursal no registrada: " + sucId);
        }
    }

    @Override
    public String toString() {
        return String.format("Inventario[sucursales=%d]", stock.size());
    }
}
