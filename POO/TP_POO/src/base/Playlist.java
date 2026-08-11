package base;

import excepciones.ContenidoNoDisponibleException;
import excepciones.PlanInsuficienteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase comun que representa una lista de reproduccion de UADE Beats.
 * Agrupa contenido de forma ordenada y permite reproducirlo en secuencia.
 */
public class Playlist {

    private final int id;
    private String nombre;
    private final Usuario propietario;
    private final List<Contenido> contenidos;

    public Playlist(int id, String nombre, Usuario propietario) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la playlist es obligatorio.");
        }
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.contenidos = new ArrayList<>();
    }

    public void agregarContenido(Contenido contenido) {
        if (contenido != null && !contenidos.contains(contenido)) {
            contenidos.add(contenido);
        }
    }

    public boolean eliminarContenido(Contenido contenido) {
        return contenidos.remove(contenido);
    }

    /**
     * Reproduce toda la playlist para un usuario, respetando las validaciones
     * de disponibilidad y plan de cada contenido. Los contenidos no accesibles
     * se informan pero no detienen la reproduccion del resto.
     */
    public String reproducirToda(Usuario usuario) {
        String salto = System.lineSeparator();
        String salida = "== Reproduciendo playlist: " + nombre + " ==";
        for (Contenido c : contenidos) {
            try {
                salida = salida + salto + c.reproducir(usuario);
            } catch (ContenidoNoDisponibleException | PlanInsuficienteException e) {
                salida = salida + salto + "   [omitido] " + e.getMessage();
            }
        }
        return salida;
    }

    public int cantidadContenidos() {
        return contenidos.size();
    }

    public int duracionTotalSegundos() {
        int total = 0;
        for (Contenido c : contenidos) {
            total = total + c.getDuracionSegundos();
        }
        return total;
    }

    public List<Contenido> getContenidos() {
        return Collections.unmodifiableList(contenidos);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public String mostrarDetalle() {
        String salto = System.lineSeparator();
        return "=== PLAYLIST ===" + salto
                + "Nombre      : " + nombre + salto
                + "Propietario : " + propietario.getNombreUsuario()
                + " (" + propietario.getPlan() + ")" + salto
                + "Contenidos  : " + contenidos.size() + salto
                + "Duracion    : " + formatearDuracion();
    }

    private String formatearDuracion() {
        int total = duracionTotalSegundos();
        int minutos = total / 60;
        int segundos = total % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    @Override
    public String toString() {
        return nombre + " [" + formatearDuracion() + "]";
    }
}
