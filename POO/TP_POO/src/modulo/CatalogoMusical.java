package modulo;

import base.Contenido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Clase de servicio que administra el catalogo de contenido del Modulo Musica
 * y Videos. Demuestra el uso de varias colecciones genericas:
 *  - List   : almacenamiento ordenado del contenido.
 *  - Map    : indice de acceso directo por id.
 *  - Set    : conjunto de generos unicos.
 * Y de ordenamientos por orden natural (Comparable) y a medida (Comparator).
 */
public class CatalogoMusical {

    private final List<Contenido> contenidos;
    private final Map<Integer, Contenido> indicePorId;
    private final Set<String> generos;

    public CatalogoMusical() {
        this.contenidos = new ArrayList<>();
        this.indicePorId = new HashMap<>();
        this.generos = new TreeSet<>();
    }

    /** Agrega un contenido al catalogo y actualiza los indices auxiliares. */
    public void agregar(Contenido contenido) {
        if (contenido == null || indicePorId.containsKey(contenido.getId())) {
            return;
        }
        contenidos.add(contenido);
        indicePorId.put(contenido.getId(), contenido);
        if (contenido instanceof Cancion) {
            Cancion cancion = (Cancion) contenido;
            if (cancion.getGenero() != null && !cancion.getGenero().trim().isEmpty()) {
                generos.add(cancion.getGenero());
            }
        }
    }

    /** Busca un contenido por su id usando el indice (acceso directo por Map). */
    public Contenido buscarPorId(int id) {
        return indicePorId.get(id);
    }

    /** Busca contenidos cuyo titulo contenga el texto indicado. */
    public List<Contenido> buscarPorTitulo(String texto) {
        List<Contenido> resultado = new ArrayList<>();
        if (texto == null) {
            return resultado;
        }
        String filtro = texto.toLowerCase();
        for (Contenido c : contenidos) {
            if (c.getTitulo().toLowerCase().contains(filtro)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /** Devuelve el contenido ordenado por su orden natural (titulo, via Comparable). */
    public List<Contenido> listarOrdenadoPorTitulo() {
        List<Contenido> copia = new ArrayList<>(contenidos);
        Collections.sort(copia);
        return copia;
    }

    /**
     * Devuelve el contenido ordenado por duracion usando un Comparator, con el
     * titulo como criterio de desempate (ordenamiento compuesto: thenComparing).
     */
    public List<Contenido> listarOrdenadoPorDuracion() {
        List<Contenido> copia = new ArrayList<>(contenidos);
        Collections.sort(copia, new Comparator<Contenido>() {
            @Override
            public int compare(Contenido primero, Contenido segundo) {
                int comparacionDuracion = Integer.compare(
                        primero.getDuracionSegundos(), segundo.getDuracionSegundos());
                if (comparacionDuracion != 0) {
                    return comparacionDuracion;
                }
                return primero.getTitulo().compareToIgnoreCase(segundo.getTitulo());
            }
        });
        return copia;
    }

    /** Filtra el contenido marcado como exclusivo. */
    public List<Contenido> filtrarExclusivos() {
        List<Contenido> resultado = new ArrayList<>();
        for (Contenido c : contenidos) {
            if (c.isExclusivo()) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Contenido> getContenidos() {
        return Collections.unmodifiableList(contenidos);
    }

    public Set<String> getGeneros() {
        return Collections.unmodifiableSet(generos);
    }

    public int cantidad() {
        return contenidos.size();
    }
}
