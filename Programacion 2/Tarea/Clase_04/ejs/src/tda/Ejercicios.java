package tda;

public class Ejercicios {

    // EJ 1
    public Conjunto obtenerRepetidos(Pila p) {
        Pila aux = new Pila(100);
        Conjunto vistos = new Conjunto(100);
        Conjunto repetidos = new Conjunto(100);

        while (!p.isEmpty()) {
            int x = p.pop();

            if (vistos.contains(x)) {
                repetidos.add(x);
            } else {
                vistos.add(x);
            }

            aux.push(x);
        }

        while (!aux.isEmpty()) {
            p.push(aux.pop());
        }

        return repetidos;
    }

    // EJ 2
    public boolean esCapicua(Cola c) {
        Cola aux = new Cola(100);
        Pila pila = new Pila(100);
        boolean esCapicua = true;

        while (!c.isEmpty()) {
            int x = c.dequeue();
            pila.push(x);
            aux.enqueue(x);
        }

        while (!aux.isEmpty()) {
            int x = aux.dequeue();
            if (x != pila.pop()) {
                esCapicua = false;
            }
            c.enqueue(x);
        }

        return esCapicua;
    }

    // EJ 3
    public DiccionarioSimple contarFrecuencias(Cola c) {
        Cola aux = new Cola(100);
        DiccionarioSimple d = new DiccionarioSimple();

        while (!c.isEmpty()) {
            int x = c.dequeue();

            if (d.containsKey(x)) {
                d.put(x, d.get(x) + 1);
            } else {
                d.put(x, 1);
            }

            aux.enqueue(x);
        }

        while (!aux.isEmpty()) {
            c.enqueue(aux.dequeue());
        }

        return d;
    }
}