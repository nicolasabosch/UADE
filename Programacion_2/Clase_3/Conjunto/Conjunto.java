public class Conjunto {

    private int[] elementos;
    private int cantidad;
    private int capacidad;

    public Conjunto(int capacidad) {
        this.capacidad = capacidad;
        elementos = new int[capacidad];
        cantidad = 0;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public boolean isFull() {
        return cantidad == capacidad;
    }

    public boolean contains(int x) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == x) {
                return true;
            }
        }
        return false;

    }

    // add
    public void add(int x) {
        if (!isFull() && !contains(x)) {
            elementos[cantidad] = x;
            cantidad++;
        }
    }

    // remove
    public void remove(int x) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == x) {
                // reemplazar por el último
                elementos[i] = elementos[cantidad - 1];
                cantidad--;
                return;
            }
        }
    }

    // elegir
    public int elegir() {
    if (!isEmpty()) {
    return elementos[0];
    }
    return -1;
    
    }

    public int size() {
    return cantidad;
    
    }
}