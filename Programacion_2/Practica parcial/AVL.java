public class AVL {
    private static class NodoAVL {
        int valor;
        int altura;
        NodoAVL izquierdo;
        NodoAVL derecho;

        NodoAVL(int valor) {
            this.valor = valor;
            this.altura = 1;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private NodoAVL raiz;

    public void InicializarArbol() {
        raiz = null;
    }

    public void Insertar(int valor) {
        raiz = insertar(raiz, valor);
    }

    public boolean Existe(int valor) {
        NodoAVL actual = raiz;
        while (actual != null && actual.valor != valor) {
            if (valor < actual.valor) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
        }
        return actual != null;
    }

    private int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    private int factorEquilibrio(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.derecho) - altura(nodo.izquierdo);
    }

    private void actualizarAltura(NodoAVL nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL t2 = x.derecho;

        x.derecho = y;
        y.izquierdo = t2;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL t2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = t2;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    private NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null) {
            return new NodoAVL(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertar(nodo.derecho, valor);
        } else {
            return nodo;
        }

        actualizarAltura(nodo);
        int fe = factorEquilibrio(nodo);

        if (fe < -1 && valor < nodo.izquierdo.valor) {
            return rotarDerecha(nodo);
        }

        if (fe > 1 && valor > nodo.derecho.valor) {
            return rotarIzquierda(nodo);
        }

        if (fe < -1 && valor > nodo.izquierdo.valor) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (fe > 1 && valor < nodo.derecho.valor) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }
}
