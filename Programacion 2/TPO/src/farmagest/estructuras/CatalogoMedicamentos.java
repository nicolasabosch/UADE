package farmagest.estructuras;

import farmagest.modelo.Medicamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TDA CatálogoMedicamentos
 * Mantiene el catálogo global de todos los medicamentos registrados
 * en el sistema con acceso directo por código.
 *
 * Representación interna: HashMap<String, Medicamento>
 *   - Acceso O(1) amortizado por clave (código).
 *   - buscarMedicamento es la operación de mayor frecuencia del sistema;
 *     cualquier estructura con acceso O(n) sería inaceptable a escala.
 *   - Alternativa descartada: TreeMap ofrece O(log n) y orden lexicográfico,
 *     pero el orden no es requerimiento del sistema. HashMap O(1) es superior
 *     para búsqueda pura por clave.
 *
 * Es el punto de validación de existencia antes de cualquier operación.
 */
public class CatalogoMedicamentos {

    private final Map<String, Medicamento> catalogo;

    public CatalogoMedicamentos() {
        this.catalogo = new HashMap<>();
    }

    /**
     * Agrega un medicamento al catálogo usando su código como clave.
     * Precondición: medicamento no nulo, código único dentro del catálogo.
     * Complejidad: O(1) amortizado.
     */
    public void registrar(Medicamento medicamento) {
        if (medicamento == null) throw new IllegalArgumentException("El medicamento no puede ser nulo.");
        if (catalogo.containsKey(medicamento.getCodigo())) {
            throw new IllegalStateException("Ya existe un medicamento con el código: " + medicamento.getCodigo());
        }
        catalogo.put(medicamento.getCodigo(), medicamento);
    }

    /**
     * Retorna el Medicamento con ese código; null si no existe.
     * Precondición: codigo no nulo.
     * Complejidad: O(1) amortizado.
     */
    public Medicamento buscarPorCodigo(String codigo) {
        if (codigo == null) throw new IllegalArgumentException("El código no puede ser nulo.");
        return catalogo.get(codigo);
    }

    /**
     * Retorna lista de medicamentos cuyo nombre contiene la cadena buscada.
     * Precondición: nombre no nulo.
     * Complejidad: O(n) — recorrido lineal del catálogo.
     */
    public List<Medicamento> buscarPorNombre(String nombre) {
        if (nombre == null) throw new IllegalArgumentException("El nombre no puede ser nulo.");
        List<Medicamento> resultado = new ArrayList<>();
        String busqueda = nombre.toLowerCase();
        for (Medicamento m : catalogo.values()) {
            if (m.getNombre().toLowerCase().contains(busqueda)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    /**
     * Elimina el medicamento con ese código del catálogo.
     * Precondición: codigo existente en el catálogo.
     * Complejidad: O(1) amortizado.
     */
    public void eliminar(String codigo) {
        if (!catalogo.containsKey(codigo)) {
            throw new IllegalArgumentException("No existe medicamento con código: " + codigo);
        }
        catalogo.remove(codigo);
    }

    /**
     * Retorna la colección completa de Medicamentos registrados.
     * Complejidad: O(1).
     */
    public Collection<Medicamento> listarTodos() {
        return catalogo.values();
    }

    /**
     * Retorna true si existe un medicamento con ese código.
     * Precondición: codigo no nulo.
     * Complejidad: O(1) amortizado.
     */
    public boolean contiene(String codigo) {
        if (codigo == null) return false;
        return catalogo.containsKey(codigo);
    }

    /**
     * Retorna la cantidad de medicamentos en el catálogo.
     * Complejidad: O(1).
     */
    public int cantidad() {
        return catalogo.size();
    }

    @Override
    public String toString() {
        return String.format("CatalogoMedicamentos[totalMedicamentos=%d]", catalogo.size());
    }
}
