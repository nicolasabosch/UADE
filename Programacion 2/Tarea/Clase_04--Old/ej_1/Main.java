public class Main {

    public static void main(String[] args) {
        Conjunto conjunto = new Conjunto();
        Pila pila = new Pila();
        conjunto.count(pila.elementosPila);

        System.out.println("Cantidad sin duplicados: " + conjunto.cantidad);
        for (int i = 0; i < conjunto.sinElementosDuplicados.length; i++) {
            System.out.println(conjunto.sinElementosDuplicados[i]);
        }
    }
}