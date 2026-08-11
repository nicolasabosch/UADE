public class ConjuntoLD implements ConjuntoTDA {
    private static class Nodo {
        int info;
        Nodo sig;
    }

    private Nodo primero;

    public void InicializarConjunto() {
        primero = null;
    }

    public void Agregar(int x) {
        if (!Pertenece(x)) {
            Nodo nuevo = new Nodo();
            nuevo.info = x;
            nuevo.sig = primero;
            primero = nuevo;
        }
    }

    public void Sacar(int x) {
        if (primero == null) {
            return;
        }

        if (primero.info == x) {
            primero = primero.sig;
        } else {
            Nodo actual = primero;
            while (actual.sig != null && actual.sig.info != x) {
                actual = actual.sig;
            }
            if (actual.sig != null) {
                actual.sig = actual.sig.sig;
            }
        }
    }

    public int Elegir() {
        return primero.info;
    }

    public boolean Pertenece(int x) {
        Nodo actual = primero;
        while (actual != null && actual.info != x) {
            actual = actual.sig;
        }
        return actual != null;
    }

    public boolean ConjuntoVacio() {
        return primero == null;
    }
}
