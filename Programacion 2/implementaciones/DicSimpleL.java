public class DicSimpleL implements DiccionarioSimpleTDA {
    private static class Nodo {
        int clave;
        int valor;
        Nodo sig;
    }

    private Nodo primero;

    public void InicializarDiccionario() {
        primero = null;
    }

    public void Agregar(int clave, int valor) {
        Nodo nodo = buscarNodo(clave);
        if (nodo == null) {
            Nodo nuevo = new Nodo();
            nuevo.clave = clave;
            nuevo.valor = valor;
            nuevo.sig = primero;
            primero = nuevo;
        } else {
            nodo.valor = valor;
        }
    }

    public void Eliminar(int clave) {
        if (primero == null) {
            return;
        }
        if (primero.clave == clave) {
            primero = primero.sig;
        } else {
            Nodo actual = primero;
            while (actual.sig != null && actual.sig.clave != clave) {
                actual = actual.sig;
            }
            if (actual.sig != null) {
                actual.sig = actual.sig.sig;
            }
        }
    }

    public int Recuperar(int clave) {
        return buscarNodo(clave).valor;
    }

    public ConjuntoTDA Claves() {
        ConjuntoTDA conjunto = new ConjuntoLD();
        conjunto.InicializarConjunto();
        Nodo actual = primero;
        while (actual != null) {
            conjunto.Agregar(actual.clave);
            actual = actual.sig;
        }
        return conjunto;
    }

    private Nodo buscarNodo(int clave) {
        Nodo actual = primero;
        while (actual != null && actual.clave != clave) {
            actual = actual.sig;
        }
        return actual;
    }
}
