package tda;

public class Cola {
    private int[] datos;
    private int frente;
    private int fin;
    private int cantidad;
    private int capacidad;

    public Cola(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.frente = 0;
        this.fin = -1;
        this.cantidad = 0;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public boolean isFull() {
        return cantidad == capacidad;
    }

    public void enqueue(int valor) {
        if (isFull())
            return;

        // El módulo hace que si fin + 1 es igual a capacidad, fin vuelva a 0
        fin = (fin + 1) % capacidad;
        datos[fin] = valor;
        cantidad++;
    }

    /*
     * public void enqueue(int valor) {
     * if (isFull()) return;
     * fin++;
     * datos[fin] = valor;
     * cantidad++;
     * }
     */

    public int dequeue() {
        if (isEmpty())
            return -1;

        int valor = datos[frente];
        frente = (frente + 1) % capacidad; // También circular
        cantidad--;
        return valor;
    }

    /*
     * public int dequeue() {
     * if (isEmpty()) return -1;
     * int valor = datos[frente];
     * frente++;
     * cantidad--;
     * return valor;
     * }
     */

    public int front() {
        if (isEmpty())
            return -1;
        return datos[frente];
    }
}