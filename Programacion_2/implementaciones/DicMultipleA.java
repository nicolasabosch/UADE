public class DicMultipleA implements DiccionarioMultipleTDA {
    private static final int MAX = 100;

    private static class Entrada {
        int clave;
        ConjuntoTDA valores;
    }

    private Entrada[] entradas;
    private int cantidad;

    public void InicializarDiccionario() {
        entradas = new Entrada[MAX];
        cantidad = 0;
    }

    public void Agregar(int clave, int valor) {
        int pos = buscarClave(clave);
        if (pos == -1) {
            Entrada entrada = new Entrada();
            entrada.clave = clave;
            entrada.valores = new ConjuntoLD();
            entrada.valores.InicializarConjunto();
            entrada.valores.Agregar(valor);
            entradas[cantidad] = entrada;
            cantidad++;
        } else {
            entradas[pos].valores.Agregar(valor);
        }
    }

    public void Eliminar(int clave) {
        int pos = buscarClave(clave);
        if (pos != -1) {
            entradas[pos] = entradas[cantidad - 1];
            cantidad--;
        }
    }

    public void EliminarValor(int clave, int valor) {
        int pos = buscarClave(clave);
        if (pos != -1) {
            entradas[pos].valores.Sacar(valor);
            if (entradas[pos].valores.ConjuntoVacio()) {
                Eliminar(clave);
            }
        }
    }

    public ConjuntoTDA Recuperar(int clave) {
        int pos = buscarClave(clave);
        return entradas[pos].valores;
    }

    public ConjuntoTDA Claves() {
        ConjuntoTDA conjunto = new ConjuntoLD();
        conjunto.InicializarConjunto();
        for (int i = 0; i < cantidad; i++) {
            conjunto.Agregar(entradas[i].clave);
        }
        return conjunto;
    }

    private int buscarClave(int clave) {
        int i = 0;
        while (i < cantidad && entradas[i].clave != clave) {
            i++;
        }
        if (i < cantidad) {
            return i;
        }
        return -1;
    }
}
