public class ColaPI implements ColaTDA {
    private static final int MAX = 100;
    private int[] elementos;
    private int cantidad;

    public void InicializarCola() {
        elementos = new int[MAX];
        cantidad = 0;
    }

    public void Acolar(int x) {
        elementos[cantidad] = x;
        cantidad++;
    }

    public void Desacolar() {
        for (int i = 0; i < cantidad - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        cantidad--;
    }

    public int Primero() {
        return elementos[0];
    }

    public boolean ColaVacia() {
        return cantidad == 0;
    }
}
