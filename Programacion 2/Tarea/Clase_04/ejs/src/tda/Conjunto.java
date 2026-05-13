package tda;

public class Conjunto {
    private int[] elementos;
    private int cantidad;
    private int capacidad;

    public Conjunto(int capacidad) {
        this.capacidad = capacidad;
        elementos = new int[capacidad];
        cantidad = 0;
    }

    public boolean contains(int x) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == x)
                return true;
        }
        return false;
    }

    public void add(int x) {
        if (!contains(x)) {
            elementos[cantidad] = x;
            cantidad++;
        }
    }

    public void mostrar() {
        for (int i = 0; i < cantidad; i++) {
            System.out.print(elementos[i] + " ");
        }
        System.out.println();
    }
}