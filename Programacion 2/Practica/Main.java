// Ejercicio 5 — Pila + Cola

// Dada una Pila P, generar una Cola C con los mismos elementos de la pila, en el mismo orden 
// en que aparecen desde la base hasta el tope.

// Condiciones:
// No modificar la pila original.  
// Se puede usar una pila auxiliar.  
// Indicar el costo temporal.  

// Ejemplo:
// P = [3, 8, 5, 1]
// C = [3, 8, 5, 1]

public class Main {
    public static void main(String[] args) {
        int[] P = { 3, 8, 5, 1 };
        Pila pila = new Pila(4);
        Pila aux = new Pila(4);
        // aux.inicializarPila()
        Cola cola = new Cola(4);
        // aux.inicializarCola()

        for (int i = 0; i < 4; i++) {
            pila.push(P[i]);
        }

        while (!pila.isEmpty()) {
            int x=pila.peek();
            aux.push(x);
            pila.pop();

        }
        
        aux.mostrar();

        while (!cola.isFull()) {
            cola.enqueue(aux.peek());
            aux.pop();
        }
        cola.mostrar();

    }
}
