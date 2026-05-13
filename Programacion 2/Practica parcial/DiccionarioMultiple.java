public class DiccionarioMultiple {

    class Elemento {
        int clave;
        int[] valores;
        int cantValores;

    }

    private Elemento[] elementos;
    private int cantClaves;

    public DiccionarioMultiple() {
        elementos = new Elemento[100];
        cantClaves = 0;
    }

    public void initialize() {
        elementos = new Elemento[100];
        cantClaves = 0;
    }

    public void put(int clave, int valor) {
        int posC = clave2Indice(clave);

        if (posC == -1) {
            posC = cantClaves;
            elementos[posC] = new Elemento();
            elementos[posC].clave = clave;
            elementos[posC].valores = new int[100];
            elementos[posC].cantValores = 0;
            cantClaves++;
        }

        Elemento e = elementos[posC];
        int posV = valor2Indice(e, valor);

        if (posV == -1) {
            e.valores[e.cantValores] = valor;
            e.cantValores++;
        }
    }

    public void remove(int clave) {
        int pos = clave2Indice(clave);

        if (pos != -1) {
            elementos[pos] = elementos[cantClaves - 1];
            cantClaves--;

        }
    }

    public void removeValue(int clave, int valor) {
        int posC = clave2Indice(clave);

        if (posC != -1) {
            Elemento e = elementos[posC];
            int posV = valor2Indice(e, valor);

            if (posV != -1) {
                e.valores[posV] = e.valores[e.cantValores - 1];
                e.cantValores--;

                if (e.cantValores == 0) {
                    remove(clave);

                }
            }
        }
    }

    public int[] get(int clave) {
        int pos = clave2Indice(clave);

        if (pos == -1) {
            return new int[0];
        }
        Elemento e = elementos[pos];
        int[] resultado = new int[e.cantValores];

        for (int i = 0; i < e.cantValores; i++) {
            resultado[i] = e.valores[i];
        }
        return resultado;
    }

    public boolean containsKey(int clave) {
        return clave2Indice(clave) != -1;

    }

    public int[] keys() {
        int[] resultado = new int[cantClaves];

        for (int i = 0; i < cantClaves; i++) {
            resultado[i] = elementos[i].clave;

        }

        return resultado;

    }

    public boolean isEmpty() {
        return cantClaves == 0;

    }

    private int clave2Indice(int clave) {
        int i = cantClaves - 1;

        while (i >= 0 && elementos[i].clave != clave) {
            i--;
        }
        return i;
    }

    private int valor2Indice(Elemento e, int valor) {
        int i = e.cantValores - 1;

        while (i >= 0 && e.valores[i] != valor) {
            i--;
        }

        return i;

    }

    public void mostrar() {
        if (cantClaves == 0) {
            System.out.println("El diccionario multiple esta vacio.");
            return;
        }
        for (int i = 0; i < cantClaves; i++) {
            System.out.print("Clave " + elementos[i].clave + " -> { ");

            for (int j = 0; j < elementos[i].cantValores; j++) {
                System.out.print(elementos[i].valores[j]);

                if (j < elementos[i].cantValores - 1) {
                    System.out.print(",");

                }
            }

            System.out.println(" }");
        }
    }
}