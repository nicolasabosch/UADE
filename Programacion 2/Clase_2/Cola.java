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
        if (isFull()) {
            System.out.println("Error: la cola está llena.");
            return;
        }
        fin++;
        datos[fin] = valor;
        cantidad++;
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Error: la cola está vacía.");
            return -1;

        }
        int valor = datos[frente];
        frente++;
        cantidad--;
        return valor;
    }
    public int front() {
        if (isEmpty()) {
        System.out.println("Error: la cola está vacía.");
        return -1;
        }
        return datos[frente];
    }
        public void mostrar() {
        if (isEmpty()) {
        System.out.println("La cola está vacía.");
        return;
        }
        
        System.out.print("Contenido de la cola: ");
        for (int i = frente; i <= fin; i++) {
        System.out.print(datos[i] +"");
        
        System.out.println();
        
        }
    }
}