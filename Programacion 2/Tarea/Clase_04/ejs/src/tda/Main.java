package tda;

public class Main {
    public static void main(String[] args) {

        Ejercicios ej = new Ejercicios();

        // EJ 1
        Pila p = new Pila(10);
        p.push(5);
        p.push(3);
        p.push(7);
        p.push(3);
        p.push(2);
        p.push(5);

        Conjunto rep = ej.obtenerRepetidos(p);
        System.out.print("Repetidos: ");
        rep.mostrar();

        // EJ 2
        Cola c = new Cola(10);
        c.enqueue(1);
        c.enqueue(4);
        c.enqueue(6);
        c.enqueue(4);
        c.enqueue(1);

        System.out.println("Capicua: " + ej.esCapicua(c));

        // EJ 3
        Cola c2 = new Cola(10);
        c2.enqueue(7);
        c2.enqueue(2);
        c2.enqueue(7);
        c2.enqueue(5);
        c2.enqueue(2);
        c2.enqueue(7);

        DiccionarioSimple d = ej.contarFrecuencias(c2);
        System.out.println("Frecuencias:");
        d.mostrar();
    }
}