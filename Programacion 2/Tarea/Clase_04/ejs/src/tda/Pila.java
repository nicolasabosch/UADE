package tda;

public class Pila {
    private int[] datos;
    private int tope;
    private int capacidad;

    public Pila(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.tope = -1;
    }

    public boolean isEmpty() {
        return tope == -1;
    }

    public boolean isFull() {
        return tope == capacidad - 1;
    }

    public void push(int valor) {
        if (isFull())
            return;
        tope++;
        datos[tope] = valor;
    }

    public int pop() {
        if (isEmpty())
            return -1;
        int valor = datos[tope];
        tope--;
        return valor;
    }

    public int peek() {
        if (isEmpty())
            return -1;
        return datos[tope];
    }
}