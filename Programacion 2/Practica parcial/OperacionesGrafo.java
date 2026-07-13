public class OperacionesGrafo {

    public ConjuntoTDA obtenerAdyacentes(GrafoTDA g, int v) {
        ConjuntoTDA adyacentes = new ConjuntoLD();
        adyacentes.InicializarConjunto();

        ConjuntoTDA vertices = g.Vertices();

        while (!vertices.ConjuntoVacio()) {
            int w = vertices.Elegir();
            vertices.Sacar(w);

            if (g.ExisteArista(v, w)) {
                adyacentes.Agregar(w);
            }
        }

        return adyacentes;
    }
}