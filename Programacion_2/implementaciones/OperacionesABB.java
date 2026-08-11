public class OperacionesABB {
    public static void preOrder(ABBTDA a) {
        if (!a.ArbolVacio()) {
            System.out.println(a.Raiz());
            preOrder(a.HijoIzq());
            preOrder(a.HijoDer());
        }
    }

    public static void inOrder(ABBTDA a) {
        if (!a.ArbolVacio()) {
            inOrder(a.HijoIzq());
            System.out.println(a.Raiz());
            inOrder(a.HijoDer());
        }
    }

    public static void postOrder(ABBTDA a) {
        if (!a.ArbolVacio()) {
            postOrder(a.HijoIzq());
            postOrder(a.HijoDer());
            System.out.println(a.Raiz());
        }
    }

    public static boolean existe(ABBTDA a, int x) {
        if (a.ArbolVacio()) {
            return false;
        } else if (a.Raiz() == x) {
            return true;
        } else if (x < a.Raiz()) {
            return existe(a.HijoIzq(), x);
        } else {
            return existe(a.HijoDer(), x);
        }
    }

    public static int contarNodos(ABBTDA a) {
        if (a.ArbolVacio()) {
            return 0;
        }
        return 1 + contarNodos(a.HijoIzq()) + contarNodos(a.HijoDer());
    }

    public static int contarHojas(ABBTDA a) {
        if (a.ArbolVacio()) {
            return 0;
        } else if (a.HijoIzq().ArbolVacio() && a.HijoDer().ArbolVacio()) {
            return 1;
        }
        return contarHojas(a.HijoIzq()) + contarHojas(a.HijoDer());
    }

    public static int contarInternos(ABBTDA a) {
        if (a.ArbolVacio()) {
            return 0;
        } else if (a.HijoIzq().ArbolVacio() && a.HijoDer().ArbolVacio()) {
            return 0;
        }
        return 1 + contarInternos(a.HijoIzq()) + contarInternos(a.HijoDer());
    }

    public static int altura(ABBTDA a) {
        if (a.ArbolVacio()) {
            return -1;
        }
        int alturaIzq = altura(a.HijoIzq());
        int alturaDer = altura(a.HijoDer());
        return 1 + Math.max(alturaIzq, alturaDer);
    }

    public static int profundidad(ABBTDA a, int x) {
        if (a.ArbolVacio()) {
            return -1;
        } else if (a.Raiz() == x) {
            return 0;
        } else if (x < a.Raiz()) {
            int p = profundidad(a.HijoIzq(), x);
            return p == -1 ? -1 : 1 + p;
        } else {
            int p = profundidad(a.HijoDer(), x);
            return p == -1 ? -1 : 1 + p;
        }
    }

    public static int menor(ABBTDA a) {
        if (a.HijoIzq().ArbolVacio()) {
            return a.Raiz();
        }
        return menor(a.HijoIzq());
    }

    public static int mayor(ABBTDA a) {
        if (a.HijoDer().ArbolVacio()) {
            return a.Raiz();
        }
        return mayor(a.HijoDer());
    }

    public static void mostrarPares(ABBTDA a) {
        if (!a.ArbolVacio()) {
            mostrarPares(a.HijoIzq());
            if (a.Raiz() % 2 == 0) {
                System.out.print(a.Raiz() + " ");
            }
            mostrarPares(a.HijoDer());
        }
    }

    public static int sumar(ABBTDA a) {
        if (a.ArbolVacio()) {
            return 0;
        }
        return a.Raiz() + sumar(a.HijoIzq()) + sumar(a.HijoDer());
    }

    public static int sumarMayoresQue(ABBTDA a, int x) {
        if (a.ArbolVacio()) {
            return 0;
        } else if (a.Raiz() <= x) {
            return sumarMayoresQue(a.HijoDer(), x);
        }
        return a.Raiz() + sumarMayoresQue(a.HijoIzq(), x) + sumarMayoresQue(a.HijoDer(), x);
    }

    public static void listarRango(ABBTDA a, int desde, int hasta) {
        if (!a.ArbolVacio()) {
            if (a.Raiz() > desde) {
                listarRango(a.HijoIzq(), desde, hasta);
            }
            if (a.Raiz() >= desde && a.Raiz() <= hasta) {
                System.out.print(a.Raiz() + " ");
            }
            if (a.Raiz() < hasta) {
                listarRango(a.HijoDer(), desde, hasta);
            }
        }
    }

    public static boolean estaDesbalanceado(ABBTDA a) {
        if (a.ArbolVacio()) {
            return false;
        }
        int altIzq = altura(a.HijoIzq());
        int altDer = altura(a.HijoDer());
        return Math.abs(altIzq - altDer) > 1;
    }

    public static void listarDesbalanceados(ABBTDA a) {
        if (!a.ArbolVacio()) {
            listarDesbalanceados(a.HijoIzq());
            if (estaDesbalanceado(a)) {
                System.out.print(a.Raiz() + " ");
            }
            listarDesbalanceados(a.HijoDer());
        }
    }
}
