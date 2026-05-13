public class MainEJ5 {
    public static void main(String[] args) {
        
        Pila pilaOriginal = new Pila(4);
        Pila pilaAuxiliar = new Pila(4);
        Cola colaFinal = new Cola(4);

        pilaOriginal.push(1);
        pilaOriginal.push(5);
        pilaOriginal.push(8);
        pilaOriginal.push(3);
        pilaOriginal.mostrar();


        while(!pilaOriginal.isEmpty()) {
            int x= pilaOriginal.peek();
            pilaOriginal.pop();
            pilaAuxiliar.push(x);

        }
        while (!pilaAuxiliar.isEmpty()) {
            int x= pilaAuxiliar.peek();
            pilaAuxiliar.pop();
            colaFinal.enqueue(x);
            pilaOriginal.push(x);
        }




    }
}
