package base;

/**
 * Clase comun que representa a un artista publicado en UADE Beats.
 * Es referenciada por el contenido del catalogo.
 */
public class Artista {

    private final int id;
    private String nombre;
    private String generoPrincipal;
    private String biografia;
    private boolean verificado;

    public Artista(int id, String nombre, String generoPrincipal,
                   String biografia, boolean verificado) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artista es obligatorio.");
        }
        this.id = id;
        this.nombre = nombre;
        this.generoPrincipal = generoPrincipal;
        this.biografia = biografia;
        this.verificado = verificado;
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

    public String getGeneroPrincipal() {
        return generoPrincipal;
    }

    public void setGeneroPrincipal(String generoPrincipal) {
        this.generoPrincipal = generoPrincipal;
    }

    public String getBiografia() {
        return biografia;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    @Override
    public String toString() {
        return nombre + (verificado ? " [verificado]" : "");
    }
}
