public class ColaPU implements ColaTDA {
    private static final int MAX = 100;
    private int[] elementos;
    private int cantidad;

    public void InicializarCola() {
        elementos = new int[MAX];
        cantidad = 0;
    }

    public void Acolar(int x) {
        for (int i = cantidad; i > 0; i--) {
            elementos[i] = elementos[i - 1];
        }
        elementos[0] = x;
        cantidad++;
    }

    public void Desacolar() {
        cantidad--;
    }

    public int Primero() {
        return elementos[cantidad - 1];
    }

    public boolean ColaVacia() {
        return cantidad == 0;
    }
}
