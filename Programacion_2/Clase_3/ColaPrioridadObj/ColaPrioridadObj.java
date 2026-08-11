class NodoPrioridad {
    int dato;
    int prioridad;

    public NodoPrioridad(int dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
    }
}

public class ColaPrioridadObj {

    private NodoPrioridad[] cola;
    private int cantidad;
    private int capacidad;

    public ColaPrioridadObj(int capacidad) {
        this.capacidad = capacidad;
        cola = new NodoPrioridad[capacidad];
        cantidad = 0;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public boolean isFull() {
        return cantidad == capacidad;
    }

    public void acolarPrioridad(int x, int p) {
        if (isFull())
            return;

        int i = cantidad - 1;

        // desplazar mientras la prioridad sea menor
        while (i >= 0 && cola[i].prioridad < p) {
            cola[i + 1] = cola[i];
            i--;
        }

        cola[i + 1] = new NodoPrioridad(x, p);
        cantidad++;
    }

    public int desacolar() {
        if (isEmpty())
            return -1;

        int valor = cola[0].dato;

        for (int i = 0; i < cantidad - 1; i++) {
            cola[i] = cola[i + 1];
        }
        cantidad--;
        return valor;
    }

    public int primero() {
        if (isEmpty())
            return -1;
        return cola[0].dato;
    }

    public int prioridad() {
        if (isEmpty())
            return -1;
        return cola[0].prioridad;
    }
}