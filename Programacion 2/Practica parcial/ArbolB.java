public class ArbolB {
    public static class NodoB {
        int[] claves;
        NodoB[] hijos;
        int cantidadClaves;
        boolean hoja;

        NodoB(int orden, boolean hoja) {
            claves = new int[orden];
            hijos = new NodoB[orden + 1];
            cantidadClaves = 0;
            this.hoja = hoja;
        }
    }

    private static class Division {
        int clavePromovida;
        NodoB derecho;
    }

    private final int orden;
    private final int maxClaves;
    private NodoB raiz;

    public ArbolB() {
        this(5);
    }

    public ArbolB(int orden) {
        this.orden = orden;
        this.maxClaves = orden - 1;
        this.raiz = new NodoB(orden, true);
    }

    public boolean Buscar(int clave) {
        return buscar(raiz, clave);
    }

    public void Insertar(int clave) {
        Division division = insertar(raiz, clave);
        if (division != null) {
            NodoB nuevaRaiz = new NodoB(orden, false);
            nuevaRaiz.claves[0] = division.clavePromovida;
            nuevaRaiz.hijos[0] = raiz;
            nuevaRaiz.hijos[1] = division.derecho;
            nuevaRaiz.cantidadClaves = 1;
            raiz = nuevaRaiz;
        }
    }

    private boolean buscar(NodoB nodo, int clave) {
        int i = 0;
        while (i < nodo.cantidadClaves && clave > nodo.claves[i]) {
            i++;
        }

        if (i < nodo.cantidadClaves && clave == nodo.claves[i]) {
            return true;
        }

        if (nodo.hoja) {
            return false;
        }

        return buscar(nodo.hijos[i], clave);
    }

    private Division insertar(NodoB nodo, int clave) {
        int pos = posicion(nodo, clave);

        if (pos < nodo.cantidadClaves && nodo.claves[pos] == clave) {
            return null;
        }

        if (nodo.hoja) {
            insertarClaveEnNodo(nodo, clave, null, pos);
        } else {
            Division divisionHijo = insertar(nodo.hijos[pos], clave);
            if (divisionHijo != null) {
                insertarClaveEnNodo(nodo, divisionHijo.clavePromovida, divisionHijo.derecho, pos);
            }
        }

        if (nodo.cantidadClaves > maxClaves) {
            return dividir(nodo);
        }

        return null;
    }

    private int posicion(NodoB nodo, int clave) {
        int pos = 0;
        while (pos < nodo.cantidadClaves && nodo.claves[pos] < clave) {
            pos++;
        }
        return pos;
    }

    private void insertarClaveEnNodo(NodoB nodo, int clave, NodoB hijoDerecho, int pos) {
        for (int i = nodo.cantidadClaves; i > pos; i--) {
            nodo.claves[i] = nodo.claves[i - 1];
        }

        if (!nodo.hoja) {
            for (int i = nodo.cantidadClaves + 1; i > pos + 1; i--) {
                nodo.hijos[i] = nodo.hijos[i - 1];
            }
            nodo.hijos[pos + 1] = hijoDerecho;
        }

        nodo.claves[pos] = clave;
        nodo.cantidadClaves++;
    }

    private Division dividir(NodoB nodo) {
        int medio = nodo.cantidadClaves / 2;
        int clavePromovida = nodo.claves[medio];
        NodoB derecho = new NodoB(orden, nodo.hoja);

        int j = 0;
        for (int i = medio + 1; i < nodo.cantidadClaves; i++) {
            derecho.claves[j] = nodo.claves[i];
            derecho.cantidadClaves++;
            j++;
        }

        if (!nodo.hoja) {
            j = 0;
            for (int i = medio + 1; i <= nodo.cantidadClaves; i++) {
                derecho.hijos[j] = nodo.hijos[i];
                nodo.hijos[i] = null;
                j++;
            }
        }

        nodo.cantidadClaves = medio;

        Division division = new Division();
        division.clavePromovida = clavePromovida;
        division.derecho = derecho;
        return division;
    }
}
