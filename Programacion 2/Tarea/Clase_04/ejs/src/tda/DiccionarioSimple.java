package tda;

public class DiccionarioSimple {

    class Elemento {
        int clave;
        int valor;
    }

    private Elemento[] elementos;
    private int cant;

    public DiccionarioSimple() {
        elementos = new Elemento[100];
        cant = 0;
    }

    public void put(int clave, int valor) {
        int pos = clave2Indice(clave);

        if (pos == -1) {
            pos = cant;
            elementos[pos] = new Elemento();
            elementos[pos].clave = clave;
            cant++;
        }

        elementos[pos].valor = valor;
    }

    public int get(int clave) {
        int pos = clave2Indice(clave);
        return elementos[pos].valor;
    }

    public boolean containsKey(int clave) {
        return clave2Indice(clave) != -1;
    }

    private int clave2Indice(int clave) {
        int i = cant - 1;
        while (i >= 0 && elementos[i].clave != clave) {
            i--;
        }
        return i;
    }

    public void mostrar() {
        for (int i = 0; i < cant; i++) {
            System.out.println(elementos[i].clave + " -> " + elementos[i].valor);
        }
    }
}