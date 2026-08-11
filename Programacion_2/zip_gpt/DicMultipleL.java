public class DicMultipleL implements DiccionarioMultipleTDA {
    private static class NodoValor {
        int valor;
        NodoValor sigValor;
    }

    private static class NodoClave {
        int clave;
        NodoValor valores;
        NodoClave sigClave;
    }

    private NodoClave primero;

    public void InicializarDiccionario() {
        primero = null;
    }

    public void Agregar(int clave, int valor) {
        NodoClave nodoClave = buscarClave(clave);
        if (nodoClave == null) {
            nodoClave = new NodoClave();
            nodoClave.clave = clave;
            nodoClave.valores = null;
            nodoClave.sigClave = primero;
            primero = nodoClave;
        }

        if (!perteneceValor(nodoClave.valores, valor)) {
            NodoValor nuevoValor = new NodoValor();
            nuevoValor.valor = valor;
            nuevoValor.sigValor = nodoClave.valores;
            nodoClave.valores = nuevoValor;
        }
    }

    public void Eliminar(int clave) {
        if (primero == null) {
            return;
        }
        if (primero.clave == clave) {
            primero = primero.sigClave;
        } else {
            NodoClave actual = primero;
            while (actual.sigClave != null && actual.sigClave.clave != clave) {
                actual = actual.sigClave;
            }
            if (actual.sigClave != null) {
                actual.sigClave = actual.sigClave.sigClave;
            }
        }
    }

    public void EliminarValor(int clave, int valor) {
        NodoClave nodoClave = buscarClave(clave);
        if (nodoClave == null || nodoClave.valores == null) {
            return;
        }

        if (nodoClave.valores.valor == valor) {
            nodoClave.valores = nodoClave.valores.sigValor;
        } else {
            NodoValor actual = nodoClave.valores;
            while (actual.sigValor != null && actual.sigValor.valor != valor) {
                actual = actual.sigValor;
            }
            if (actual.sigValor != null) {
                actual.sigValor = actual.sigValor.sigValor;
            }
        }

        if (nodoClave.valores == null) {
            Eliminar(clave);
        }
    }

    public ConjuntoTDA Recuperar(int clave) {
        NodoClave nodoClave = buscarClave(clave);
        ConjuntoTDA conjunto = new ConjuntoLD();
        conjunto.InicializarConjunto();

        NodoValor actual = nodoClave.valores;
        while (actual != null) {
            conjunto.Agregar(actual.valor);
            actual = actual.sigValor;
        }

        return conjunto;
    }

    public ConjuntoTDA Claves() {
        ConjuntoTDA conjunto = new ConjuntoLD();
        conjunto.InicializarConjunto();
        NodoClave actual = primero;
        while (actual != null) {
            conjunto.Agregar(actual.clave);
            actual = actual.sigClave;
        }
        return conjunto;
    }

    private NodoClave buscarClave(int clave) {
        NodoClave actual = primero;
        while (actual != null && actual.clave != clave) {
            actual = actual.sigClave;
        }
        return actual;
    }

    private boolean perteneceValor(NodoValor primeroValor, int valor) {
        NodoValor actual = primeroValor;
        while (actual != null && actual.valor != valor) {
            actual = actual.sigValor;
        }
        return actual != null;
    }
}
