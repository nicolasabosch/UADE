public class ColaPrioridadAO implements ColaPrioridadTDA {
    private static final int MAX = 100;

    private static class ElementoPrioridad {
        int valor;
        int prioridad;
    }

    private ElementoPrioridad[] elementos;
    private int cantidad;

    public void InicializarCola() {
        elementos = new ElementoPrioridad[MAX];
        cantidad = 0;
    }

    public void AcolarPrioridad(int x, int prioridad) {
        ElementoPrioridad nuevo = new ElementoPrioridad();
        nuevo.valor = x;
        nuevo.prioridad = prioridad;

        int pos = 0;
        while (pos < cantidad && elementos[pos].prioridad < prioridad) {
            pos++;
        }

        for (int i = cantidad; i > pos; i--) {
            elementos[i] = elementos[i - 1];
        }

        elementos[pos] = nuevo;
        cantidad++;
    }

    public void Desacolar() {
        cantidad--;
    }

    public int Primero() {
        return elementos[cantidad - 1].valor;
    }

    public int Prioridad() {
        return elementos[cantidad - 1].prioridad;
    }

    public boolean ColaVacia() {
        return cantidad == 0;
    }
}
