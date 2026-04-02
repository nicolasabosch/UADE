public class Main {
    public static void main(String[] args) {

        DiccionarioMultiple dic = new DiccionarioMultiple();

        dic.put(3, 95);
        dic.put(3, 7);
        dic.put(3, 8);

        dic.put(5, 10);
        dic.put(5, 4);
        dic.put(5, 9);

        dic.put(7, 45);
        dic.put(7, 22);

        System.out.println("Estado inicial del diccionario:");
        dic.mostrar();

        System.out.println("\nAgregar valor repetido a clave 3:");
        dic.put(3, 95); // no lo agrega porque ya existe
        dic.mostrar();

        System.out.println("\nValores asociados a la clave 5:");
        int[] valores = dic.get(5);
        for (int i = 0; i < valores.length; i++) {
            System.out.print(valores[i] + "");
        }

        System.out.println("\n\nEliminar valor 4 de la clave 5:");
        dic.removeValue(5, 4);
        dic.mostrar();

        System.out.println("\nEliminar clave 7 completa:");
        dic.remove(7);
        dic.mostrar();

        System.out.println("\nClaves almacenadas:");
        int[] claves = dic.keys();
        for (int i = 0; i < claves.length; i++) {
            System.out.print(claves[i] + "");
        }

        System.out.println("\n\nExiste la clave 3? " + dic.containsKey(3));
        System.out.println("Existe la clave 7? " + dic.containsKey(7));
        System.out.println("El diccionario esta vacio? " + dic.isEmpty());

    }

}