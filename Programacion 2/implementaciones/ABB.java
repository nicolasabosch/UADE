public class ABB implements ABBTDA {
    private static class NodoABB {
        int info;
        ABB hijoIzq;
        ABB hijoDer;
    }

    private NodoABB raiz;

    public void InicializarArbol() {
        raiz = null;
    }

    public boolean ArbolVacio() {
        return raiz == null;
    }

    public int Raiz() {
        return raiz.info;
    }

    public ABBTDA HijoIzq() {
        return raiz.hijoIzq;
    }

    public ABBTDA HijoDer() {
        return raiz.hijoDer;
    }

    public void AgregarElem(int x) {
        if (raiz == null) {
            raiz = new NodoABB();
            raiz.info = x;
            raiz.hijoIzq = new ABB();
            raiz.hijoIzq.InicializarArbol();
            raiz.hijoDer = new ABB();
            raiz.hijoDer.InicializarArbol();
        } else if (x < raiz.info) {
            raiz.hijoIzq.AgregarElem(x);
        } else if (x > raiz.info) {
            raiz.hijoDer.AgregarElem(x);
        }
    }

    public void EliminarElem(int x) {
        if (raiz == null) {
            return;
        }

        if (x < raiz.info) {
            raiz.hijoIzq.EliminarElem(x);
        } else if (x > raiz.info) {
            raiz.hijoDer.EliminarElem(x);
        } else {
            if (raiz.hijoIzq.ArbolVacio() && raiz.hijoDer.ArbolVacio()) {
                raiz = null;
            } else if (!raiz.hijoIzq.ArbolVacio()) {
                int reemplazo = mayor(raiz.hijoIzq);
                raiz.info = reemplazo;
                raiz.hijoIzq.EliminarElem(reemplazo);
            } else {
                int reemplazo = menor(raiz.hijoDer);
                raiz.info = reemplazo;
                raiz.hijoDer.EliminarElem(reemplazo);
            }
        }
    }

    private int mayor(ABB arbol) {
        if (arbol.raiz.hijoDer.ArbolVacio()) {
            return arbol.raiz.info;
        }
        return mayor(arbol.raiz.hijoDer);
    }

    private int menor(ABB arbol) {
        if (arbol.raiz.hijoIzq.ArbolVacio()) {
            return arbol.raiz.info;
        }
        return menor(arbol.raiz.hijoIzq);
    }
}
