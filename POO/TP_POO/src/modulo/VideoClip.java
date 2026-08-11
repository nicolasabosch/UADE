package modulo;

/**
 * Clase concreta que representa un videoclip en UADE Beats.
 * Hereda de ContenidoAudiovisual y puede asociarse a una Cancion.
 */
public class VideoClip extends ContenidoAudiovisual {

    private String resolucion;
    private boolean tieneSubtitulos;
    private String urlVideo;
    private Cancion cancionAsociada;

    public VideoClip(int id, String titulo, int duracionSegundos, String descripcion,
                     String fechaPublicacion, boolean disponible, boolean exclusivo,
                     String resolucion, boolean tieneSubtitulos, String urlVideo,
                     Cancion cancionAsociada) {
        super(id, titulo, duracionSegundos, descripcion, fechaPublicacion, disponible, exclusivo);
        this.resolucion = resolucion;
        this.tieneSubtitulos = tieneSubtitulos;
        this.urlVideo = urlVideo;
        this.cancionAsociada = cancionAsociada;
    }

    @Override
    public String reproducir() {
        return ">> Reproduciendo videoclip: " + titulo + " [" + resolucion + "]"
                + (tieneSubtitulos ? " (con subtitulos)" : "");
    }

    @Override
    public String mostrarDetalle() {
        String salto = System.lineSeparator();
        String detalle = "=== VIDEOCLIP ===" + salto
                + "Titulo     : " + titulo + salto
                + "Resolucion : " + resolucion + salto
                + "Subtitulos : " + (tieneSubtitulos ? "Si" : "No");
        if (cancionAsociada != null) {
            detalle = detalle + salto + "Cancion    : " + cancionAsociada.getTitulo();
        }
        return detalle + salto + "Likes      : " + getCantidadLikes();
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public boolean isTieneSubtitulos() {
        return tieneSubtitulos;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public Cancion getCancionAsociada() {
        return cancionAsociada;
    }

    public void setCancionAsociada(Cancion cancionAsociada) {
        this.cancionAsociada = cancionAsociada;
    }
}
