public class MainEJ4 {
    public static void mainEJ4(String[] args) {
        Cola cola = new Cola(6);
        cola.enqueue(4);
        cola.enqueue(2);
        cola.enqueue(4);
        cola.enqueue(7);
        cola.enqueue(2);
        cola.enqueue(9);

        Cola colaAuxiliar = new Cola(6);
        Cola r= new Cola(4);
        Conjunto conjuntoAuxiliar = new Conjunto(4);

        while (!cola.isEmpty()) {
            int x= cola.front();
            cola.dequeue();
            colaAuxiliar.enqueue(x);

            if (!conjuntoAuxiliar.contains(x)) {
                conjuntoAuxiliar.add(x);
                r.enqueue(x);
            }
        }

        while (!colaAuxiliar.isEmpty()) {
            int x= colaAuxiliar.front();
            colaAuxiliar.dequeue();
            cola.enqueue(x);

            
        }
        //return r
        
    }

}