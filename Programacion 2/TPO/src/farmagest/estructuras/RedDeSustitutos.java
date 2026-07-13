package farmagest.estructuras;

import java.util.*;

/**
 * TDA RedDeSustitutos
 * Modela las relaciones de equivalencia terapéutica entre medicamentos
 * como un grafo no dirigido.
 *
 * Representación interna: lista de adyacencia
 *   HashMap<String, List<String>>
 *   - Cada nodo es un medicamento (identificado por su código).
 *   - Cada arista representa que dos medicamentos son sustitutos entre sí.
 *   - Acceso a vecinos de un nodo: O(1).
 *   - BFS para búsqueda de sustituto: O(V + E).
 *
 * Alternativa descartada: Matriz de adyacencia.
 *   Con 10.000 medicamentos: 10.000² = 100.000.000 celdas — inviable en espacio.
 *   La lista de adyacencia es la representación estándar para grafos dispersos.
 *
 * Alternativa descartada: Árbol de sustitutos.
 *   La relación de sustitución es simétrica (si A sustituye a B, B sustituye a A)
 *   y puede presentar múltiples conexiones y ciclos. Un árbol, por definición
 *   acíclica y jerárquica, no puede modelar correctamente estas relaciones.
 *
 * Propiedad de simetría: agregarArista() registra la relación en ambas
 * direcciones garantizando el grafo no dirigido.
 */
public class RedDeSustitutos {

    private final Map<String, List<String>> adyacencia;

    public RedDeSustitutos() {
        this.adyacencia = new HashMap<>();
    }

    /**
     * Agrega el medicamento como nodo al grafo con lista de adyacencia vacía.
     * Precondición: medId válido y no existente en el grafo.
     * Complejidad: O(1).
     */
    public void agregarNodo(String medId) {
        if (medId == null || medId.isBlank()) throw new IllegalArgumentException("El ID de medicamento no puede ser nulo.");
        if (adyacencia.containsKey(medId)) {
            throw new IllegalStateException("El medicamento ya está registrado en la red: " + medId);
        }
        adyacencia.put(medId, new ArrayList<>());
    }

    /**
     * Registra la relación de sustitución bidireccional entre dos medicamentos.
     * Verifica que no se agreguen aristas duplicadas y que un medicamento
     * no pueda ser sustituto de sí mismo.
     * Precondición: ambos medId registrados en el grafo.
     * Complejidad: O(1) amortizado.
     */
    public void agregarArista(String medId1, String medId2) {
        validarNodo(medId1);
        validarNodo(medId2);
        if (medId1.equals(medId2)) throw new IllegalArgumentException("Un medicamento no puede ser sustituto de sí mismo.");
        if (!adyacencia.get(medId1).contains(medId2)) {
            adyacencia.get(medId1).add(medId2);
        }
        if (!adyacencia.get(medId2).contains(medId1)) {
            adyacencia.get(medId2).add(medId1);
        }
    }

    /**
     * Ejecuta BFS desde medId buscando el sustituto disponible más cercano.
     *
     * El nodo origen se excluye explícitamente del resultado: se busca un
     * sustituto DISTINTO al medicamento solicitado. Esto corrige el caso
     * en que el nodo origen se retornaría como su propio sustituto cuando
     * tiene stock (ya que es el primer nodo visitado por el BFS).
     *
     * Para cada nodo visitado (distinto del origen) se consulta hayStock()
     * en el Inventario en O(1) via acumulador stockTotal.
     *
     * BFS garantiza que el sustituto retornado sea el de menor distancia
     * terapéutica (cantidad de saltos) desde el medicamento solicitado.
     *
     * Precondición: medId registrado, inventario e sucId válidos.
     * Complejidad: O(V + E).
     */
    public String buscarSustitutoDisponible(String medId, Inventario inventario, String sucId) {
        validarNodo(medId);
        if (inventario == null) throw new IllegalArgumentException("El inventario no puede ser nulo.");

        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();

        visitados.add(medId);
        cola.offer(medId);

        while (!cola.isEmpty()) {
            String actual = cola.poll();

            // Excluir nodo origen: solo retornar sustitutos DISTINTOS al medicamento solicitado
            if (!actual.equals(medId) && inventario.hayStock(sucId, actual)) {
                return actual;
            }

            List<String> vecinos = adyacencia.get(actual);
            if (vecinos != null) {
                for (String vecino : vecinos) {
                    if (!visitados.contains(vecino)) {
                        visitados.add(vecino);
                        cola.offer(vecino);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Retorna la lista de medicamentos directamente sustitutos del indicado.
     * Precondición: medId registrado en el grafo.
     * Complejidad: O(1).
     */
    public List<String> getVecinos(String medId) {
        validarNodo(medId);
        return new ArrayList<>(adyacencia.get(medId));
    }

    /**
     * Verifica conectividad entre dos medicamentos mediante BFS.
     * Precondición: ambos medId válidos.
     * Complejidad: O(V + E).
     */
    public boolean estaConectado(String medId1, String medId2) {
        validarNodo(medId1);
        validarNodo(medId2);

        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.offer(medId1);
        visitados.add(medId1);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(medId2)) return true;
            List<String> vecinos = adyacencia.get(actual);
            if (vecinos != null) {
                for (String vecino : vecinos) {
                    if (!visitados.contains(vecino)) {
                        visitados.add(vecino);
                        cola.offer(vecino);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retorna el total de medicamentos registrados en la red.
     * Complejidad: O(1).
     */
    public int cantidadNodos() {
        return adyacencia.size();
    }

    /**
     * Retorna true si el medicamento está registrado como nodo.
     */
    public boolean contieneNodo(String medId) {
        return adyacencia.containsKey(medId);
    }

    private void validarNodo(String medId) {
        if (medId == null || !adyacencia.containsKey(medId)) {
            throw new IllegalArgumentException("Medicamento no registrado en la red: " + medId);
        }
    }

    @Override
    public String toString() {
        int totalDirecciones = 0;
        for (List<String> vecinos : adyacencia.values()) {
            totalDirecciones += vecinos.size();
        }
        int totalAristas = totalDirecciones / 2; // grafo no dirigido: cada arista se cuenta dos veces
        return String.format("RedDeSustitutos[nodos=%d, aristas=%d]", adyacencia.size(), totalAristas);
    }
}
