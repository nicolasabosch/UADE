public class ColaLD implements ColaTDA {
    private static class Nodo {
        int info;
        Nodo sig;
    }

    private Nodo primero;
    private Nodo ultimo;

    public void InicializarCola() {
        primero = null;
        ultimo = null;
    }

    public void Acolar(int x) {
        Nodo nuevo = new Nodo();
        nuevo.info = x;
        nuevo.sig = null;

        if (ultimo != null) {
            ultimo.sig = nuevo;
        }
        ultimo = nuevo;

        if (primero == null) {
            primero = ultimo;
        }
    }

    public void Desacolar() {
        primero = primero.sig;
        if (primero == null) {
            ultimo = null;
        }
    }

    public int Primero() {
        return primero.info;
    }

    public boolean ColaVacia() {
        return primero == null;
    }
}
