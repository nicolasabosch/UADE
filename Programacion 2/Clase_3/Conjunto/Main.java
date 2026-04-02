public class Main {
    public static void main(String[] args) {

        Conjunto c = new Conjunto(10);

        c.add(5);
        c.add(8);
        c.add(3);
        c.add(8); // no se agrega (duplicado)

        System.out.println("Elemento elegido: " + c.elegir());

        c.remove(8);

        System.out.println("Después de eliminar 8:");
        while (!c.isEmpty()) {
            int x = c.elegir();
            System.out.println(x);
            c.remove(x);

        }

    }

}