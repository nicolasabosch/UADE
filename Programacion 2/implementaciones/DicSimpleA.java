public class DicSimpleA implements DiccionarioSimpleTDA {
    private static final int MAX = 100;
    private int[] claves;
    private int[] valores;
    private int cantidad;

    public void InicializarDiccionario() {
        claves = new int[MAX];
        valores = new int[MAX];
        cantidad = 0;
    }

    public void Agregar(int clave, int valor) {
        int pos = buscarClave(clave);
        if (pos == -1) {
            claves[cantidad] = clave;
            valores[cantidad] = valor;
            cantidad++;
        } else {
            valores[pos] = valor;
        }
    }

    public void Eliminar(int clave) {
        int pos = buscarClave(clave);
        if (pos != -1) {
            claves[pos] = claves[cantidad - 1];
            valores[pos] = valores[cantidad - 1];
            cantidad--;
        }
    }

    public int Recuperar(int clave) {
        int pos = buscarClave(clave);
        return valores[pos];
    }

    public ConjuntoTDA Claves() {
        ConjuntoTDA conjunto = new ConjuntoLD();
        conjunto.InicializarConjunto();
        for (int i = 0; i < cantidad; i++) {
            conjunto.Agregar(claves[i]);
        }
        return conjunto;
    }

    private int buscarClave(int clave) {
        int i = 0;
        while (i < cantidad && claves[i] != clave) {
            i++;
        }
        if (i < cantidad) {
            return i;
        }
        return -1;
    }
}
