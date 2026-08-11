package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase comun que representa a un usuario de la plataforma UADE Beats.
 * Encapsula sus credenciales, su plan de suscripcion y su lista de favoritos.
 */
public class Usuario {

    private final int id;
    private final String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private PlanSuscripcion plan;
    private boolean sesionActiva;
    private final List<Contenido> favoritos;
    private final List<Playlist> playlists;

    public Usuario(int id, String nombreUsuario, String nombre, String apellido,
                   String email, String password, PlanSuscripcion plan) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }
        if (plan == null) {
            throw new IllegalArgumentException("El plan de suscripcion es obligatorio.");
        }
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.plan = plan;
        this.sesionActiva = false;
        this.favoritos = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    /** Inicia la sesion del usuario validando la contrasena. */
    public boolean iniciarSesion(String passwordIngresada) {
        if (password != null && password.equals(passwordIngresada)) {
            this.sesionActiva = true;
        }
        return this.sesionActiva;
    }

    /** Cierra la sesion del usuario. */
    public void cerrarSesion() {
        this.sesionActiva = false;
    }

    /**
     * Cambia el plan de suscripcion del usuario.
     * @return true si el plan fue modificado, false si ya tenia ese plan.
     */
    public boolean cambiarPlan(PlanSuscripcion nuevoPlan) {
        if (nuevoPlan == null) {
            throw new IllegalArgumentException("El plan de suscripcion es obligatorio.");
        }
        if (this.plan == nuevoPlan) {
            return false;
        }
        this.plan = nuevoPlan;
        return true;
    }

    /** Actualiza los datos de perfil del usuario. */
    public void actualizarPerfil(String nombre, String apellido, String email) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
        if (apellido != null && !apellido.trim().isEmpty()) {
            this.apellido = apellido;
        }
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        }
    }

    /**
     * Indica si el usuario puede acceder a un contenido segun su plan.
     * Regla: el contenido exclusivo solo es accesible por planes que lo permitan.
     */
    public boolean accederContenido(boolean contenidoExclusivo) {
        if (!contenidoExclusivo) {
            return true;
        }
        return plan.permiteContenidoExclusivo();
    }

    /** Indica si el usuario puede acceder a entrevistas grabadas segun su plan. */
    public boolean accederEntrevista() {
        return plan.permiteEntrevistas();
    }

    /** Agrega un contenido a la lista de favoritos del usuario (sin duplicados). */
    public void agregarAFavoritos(Contenido contenido) {
        if (contenido != null && !favoritos.contains(contenido)) {
            favoritos.add(contenido);
        }
    }

    public List<Contenido> getFavoritos() {
        return Collections.unmodifiableList(favoritos);
    }

    /** Agrega una playlist al usuario (sin duplicados). */
    public void agregarPlaylist(Playlist playlist) {
        if (playlist != null && !playlists.contains(playlist)) {
            playlists.add(playlist);
        }
    }

    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public PlanSuscripcion getPlan() {
        return plan;
    }

    public void setPlan(PlanSuscripcion plan) {
        if (plan != null) {
            this.plan = plan;
        }
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }

    @Override
    public String toString() {
        return nombreUsuario + " (" + plan + ")";
    }
}
