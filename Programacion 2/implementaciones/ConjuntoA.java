public class ConjuntoA implements ConjuntoTDA {
    private static final int MAX = 100;
    private int[] elementos;
    private int cantidad;

    public void InicializarConjunto() {
        elementos = new int[MAX];
        cantidad = 0;
    }

    public void Agregar(int x) {
        if (!Pertenece(x)) {
            elementos[cantidad] = x;
            cantidad++;
        }
    }

    public void Sacar(int x) {
        int i = 0;
        while (i < cantidad && elementos[i] != x) {
            i++;
        }
        if (i < cantidad) {
            elementos[i] = elementos[cantidad - 1];
            cantidad--;
        }
    }

    public int Elegir() {
        return elementos[cantidad - 1];
    }

    public boolean Pertenece(int x) {
        int i = 0;
        while (i < cantidad && elementos[i] != x) {
            i++;
        }
        return i < cantidad;
    }

    public boolean ConjuntoVacio() {
        return cantidad == 0;
    }
}
