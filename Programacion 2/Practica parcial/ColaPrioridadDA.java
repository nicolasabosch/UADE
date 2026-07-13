public class ColaPrioridadDA implements ColaPrioridadTDA {
    private static final int MAX = 100;
    private int[] elementos;
    private int[] prioridades;
    private int cantidad;

    public void InicializarCola() {
        elementos = new int[MAX];
        prioridades = new int[MAX];
        cantidad = 0;
    }

    public void AcolarPrioridad(int x, int prioridad) {
        int pos = 0;
        while (pos < cantidad && prioridades[pos] < prioridad) {
            pos++;
        }

        for (int i = cantidad; i > pos; i--) {
            elementos[i] = elementos[i - 1];
            prioridades[i] = prioridades[i - 1];
        }

        elementos[pos] = x;
        prioridades[pos] = prioridad;
        cantidad++;
    }

    public void Desacolar() {
        cantidad--;
    }

    public int Primero() {
        return elementos[cantidad - 1];
    }

    public int Prioridad() {
        return prioridades[cantidad - 1];
    }

    public boolean ColaVacia() {
        return cantidad == 0;
    }
}
