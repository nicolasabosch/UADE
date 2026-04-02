public class ColaPrioridad3Colas {
    private Cola colaAlta;
    private Cola colaMedia;
    private Cola colaBaja;

    public ColaPrioridad3Colas(int capacidadPorCola) {
        colaAlta = new Cola(capacidadPorCola);
        colaMedia = new Cola(capacidadPorCola);
        colaBaja = new Cola(capacidadPorCola);
    }

    public boolean isEmpty() {
        return colaAlta.isEmpty() && colaMedia.isEmpty() && colaBaja.isEmpty();
    }

    public boolean isFull() {
        return colaAlta.isFull() || colaMedia.isFull() || colaBaja.isFull();
    }

    public void enqueuePriority(int x, int prioridad) {
        switch (prioridad) {
            case 3:
                colaAlta.enqueue(x);
                break;
            case 2:
                colaMedia.enqueue(x);
                break;
            case 1:
                colaBaja.enqueue(x);
                break;
            default:
                System.out.println("Prioridad inválida");
        }
    }

    public int dequeue() {
        if (!colaAlta.isEmpty()) {
            return colaAlta.dequeue();
        } else if (!colaMedia.isEmpty()) {
            return colaMedia.dequeue();
        } else if (!colaBaja.isEmpty()) {
            return colaBaja.dequeue();
        }
        return -1;
    }

    public int front() {
        if (!colaAlta.isEmpty()) {
            return colaAlta.front();
        } else if (!colaMedia.isEmpty()) {
            return colaMedia.front();
        } else if (!colaBaja.isEmpty()) {
            return colaBaja.front();
        }
        return -1;
    }

    public int priority() {
        if (!colaAlta.isEmpty()) {
            return 3;
        } else if (!colaMedia.isEmpty()) {
            return 2;
        } else if (!colaBaja.isEmpty()) {
            return 1;
        }
        return -1;
    }
}

// --- CLASE COLA (IMPLEMENTACIÓN CIRCULAR) ---
class Cola {
    private int[] datos;
    private int frente;
    private int fin;
    private int cantidad;
    private int capacidad;

    public Cola(int capacidad) {
        this.capacidad = capacidad;
        datos = new int[capacidad];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public boolean isFull() {
        return cantidad == capacidad;
    }

    public void enqueue(int x) {
        if (!isFull()) {
            fin = (fin + 1) % capacidad;
            datos[fin] = x;
            cantidad++;
        }
    }

    public int dequeue() {
        if (isEmpty()) {
            return -1;
        }
        int valor = datos[frente];
        frente = (frente + 1) % capacidad;
        cantidad--;
        return valor;
    }

    public int front() {
        if (isEmpty()) {
            return -1;
        }
        return datos[frente];
    }
}