package modulo;

import base.Artista;

/**
 * Clase concreta que representa una cancion (pista de audio) en UADE Beats.
 * Hereda de ContenidoAudiovisual, por lo que es reproducible e interactuable.
 */
public class Cancion extends ContenidoAudiovisual {

    private String genero;
    private Artista artista;
    private String letra;

    public Cancion(int id, String titulo, int duracionSegundos, String descripcion,
                   String fechaPublicacion, boolean disponible, boolean exclusivo,
                   String genero, Artista artista, String letra) {
        super(id, titulo, duracionSegundos, descripcion, fechaPublicacion, disponible, exclusivo);
        this.genero = genero;
        this.artista = artista;
        this.letra = letra;
    }

    @Override
    public String mostrarDetalle() {
        String salto = System.lineSeparator();
        return "=== CANCION ===" + salto
                + "Titulo   : " + titulo + salto
                + "Artista  : " + getNombreArtista() + salto
                + "Genero   : " + genero + salto
                + "Duracion : " + formatearDuracion() + salto
                + "Exclusivo: " + (exclusivo ? "Si" : "No") + salto
                + "Likes    : " + getCantidadLikes();
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombreArtista() {
        return artista == null ? "Sin artista" : artista.getNombre();
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        if (artista != null) {
            this.artista = artista;
        }
    }

    public String getLetra() {
        return letra;
    }
}
