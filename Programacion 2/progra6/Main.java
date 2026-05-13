package progra6;

public class Main {

    public static void preOrder(ABBTDA a) {
        if (!a.ArbolVacio()) {
            System.out.print(a.Raiz() + " ");
            preOrder(a.HijoIzq());
            preOrder(a.HijoDer());
        }
    }

    public static void inOrder(ABBTDA a) {
        if (!a.ArbolVacio()) {
            inOrder(a.HijoIzq());
            System.out.print(a.Raiz() + " ");
            inOrder(a.HijoDer());
        }
    }

    public static void postOrder(ABBTDA a) {
        if (!a.ArbolVacio()) {
            postOrder(a.HijoIzq());
            postOrder(a.HijoDer());
            System.out.print(a.Raiz() + " ");
        }
    }

    public static boolean existe(ABBTDA a, int x) {
        if (a.ArbolVacio()) {
            return false;
        }
        if (x == a.Raiz()) {
            return true;
        }
        if (x < a.Raiz()) {
            return existe(a.HijoIzq(), x);
        }
        return existe(a.HijoDer(), x);
    }

    public static void main(String[] args) {
        ABBTDA arbol = new ABB();
        arbol.InicializarArbol();

        int[] secuencia = {50, 30, 70, 20, 40, 60, 80, 35, 45};
        for (int valor : secuencia) {
            arbol.AgregarElem(valor);
        }

        System.out.print("Preorden: ");
        preOrder(arbol);
        System.out.println();

        System.out.print("Inorden: ");
        inOrder(arbol);
        System.out.println();

        System.out.print("Postorden: ");
        postOrder(arbol);
        System.out.println();

        int[] busquedas = {60, 45, 90};
        for (int valor : busquedas) {
            System.out.println("Valor " + valor + ": " + (existe(arbol, valor) ? "existe" : "no existe"));
        }
    }
}
