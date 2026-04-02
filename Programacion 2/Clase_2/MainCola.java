public class MainCola {
    public static void main(String[] args) {
        Cola cola = new Cola(5);

        cola.enqueue(10);
        cola.enqueue(20);
        cola.enqueue(30);

        cola.mostrar();

        System.out.println("Frente actual: " + cola.front());
        System.out.println("Elemento desencolado: " + cola.dequeue());

        cola.mostrar();

    }
}