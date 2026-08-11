public class PilaTF implements PilaTDA {
    private static final int MAX = 100;
    private int[] elementos;
    private int cantidad;

    public void InicializarPila() {
        elementos = new int[MAX];
        cantidad = 0;
    }

    public void Apilar(int x) {
        elementos[cantidad] = x;
        cantidad++;
    }

    public void Desapilar() {
        cantidad--;
    }

    public int Tope() {
        return elementos[cantidad - 1];
    }

    public boolean PilaVacia() {
        return cantidad == 0;
    }
}
