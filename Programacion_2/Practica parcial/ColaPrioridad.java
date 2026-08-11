public class ColaPrioridad {

    private int[] elementos;
    private int[] prioridades;
    private int cantidad;
    private int capacidad;

    public ColaPrioridad(int capacidad) {
        this.capacidad = capacidad;
        elementos = new int[capacidad];
        prioridades = new int[capacidad];
        cantidad = 0;

    }

    public boolean isEmpty() {
        return cantidad == 0;

    }

    public boolean isFull() {
        return cantidad == capacidad;

    }

    // enqueuePriority
    public void acolarPrioridad(int x, int p) {
        if (isFull()) return;

        int i = cantidad - 1;

// desplazar mientras la prioridad sea menor
        while (i >= 0 && prioridades[i] < p) {
            elementos[i + 1] = elementos[i];
            prioridades[i + 1] = prioridades[i];
            i --;
        }
            // insertar en la posición correcta
            elementos[i + 1] = x;
            prioridades[i + 1] = p;

            cantidad++;
    }
// dequeue
            public int desacolar() {
                if (isEmpty()) return -1;

                int valor = elementos[0];

// desplazar hacia la izquierda
                for (int i = 0; i < cantidad - 1; i++) {
                    elementos[i] = elementos[i + 1];
                    prioridades[i] = prioridades[i + 1];

                }

                cantidad --;
                return valor;
            }
// front
                public int primero() {
                    if (isEmpty()) return -1;
                    return elementos[0];

                }

                public int prioridad() {
                    if (isEmpty()) return -1;
                    return prioridades[0];

                }

            }