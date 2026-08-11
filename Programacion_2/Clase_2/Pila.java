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
        if (isFull()) {
            System.out.println("Error: la pila esta llena.");
            return;
        }
        tope++;
        datos[tope] = valor;

    }

    public int pop() {
        if (isEmpty()) {
            System.out.println("Error: la pila está vacía.");
            return -1;
        }

        int valor = datos[tope];
        tope--;
        return valor;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Error: la pila está vacía.");
            return -1;
        }
        return datos[tope];

    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("La pila está vacía.");
            return;
        }

        System.out.println("Contenido de la pila:");
        for (int i = tope; i >= 0; i--) {
            System.out.println(datos[i]);
        }
    }
}