class MainColaPrioridad3Colas {
    public static void main(String[] args) {
        ColaPrioridad3Colas cola = new ColaPrioridad3Colas(10);

        cola.enqueuePriority(101, 1); // baja
        cola.enqueuePriority(202, 3); // alta
        cola.enqueuePriority(303, 2); // media
        cola.enqueuePriority(404, 3); // alta
        cola.enqueuePriority(505, 1); // baja
        cola.enqueuePriority(606, 2); // media

        System.out.println("Orden de atención:");

        while (!cola.isEmpty()) {
            System.out.println("Elemento: " + cola.front() + " | Prioridad: " + cola.priority());

            cola.dequeue();

        }
    }
}