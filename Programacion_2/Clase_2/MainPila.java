public class MainPila {
    public static void main(String[] args) {
        Pila pila = new Pila(5);

        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.mostrar();

        System.out.println("Tope actual: " + pila.peek());
        System.out.println("Elemento desapilado: " + pila.pop());

        pila.mostrar();
        
    }
}
