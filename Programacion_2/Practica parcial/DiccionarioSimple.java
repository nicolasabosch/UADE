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

    public void remove(int clave) {
        int pos = clave2Indice(clave);

        if (pos != -1) {
            elementos[pos] = elementos[cant - 1];
            cant--;

        }
    }

    public int get(int clave) {
        int pos = clave2Indice(clave);
        return elementos[pos].valor;

    }

    public boolean containsKey(int clave) {
    return clave2Indice(clave) != -1;
    }

    public boolean isEmpty() {
        return cant == 0;

    }

    private int clave2Indice(int clave) {
        int i = cant - 1;

        while (i >= 0 && elementos[i].clave != clave) {
            i--;

        }

        return i;

    }

}