public class PilaTI implements PilaTDA {
    private static final int MAX = 100;
    private int[] elementos;
    private int cantidad;

    public void InicializarPila() {
        elementos = new int[MAX];
        cantidad = 0;
    }

    public void Apilar(int x) {
        for (int i = cantidad; i > 0; i--) {
            elementos[i] = elementos[i - 1];
        }
        elementos[0] = x;
        cantidad++;
    }

    public void Desapilar() {
        for (int i = 0; i < cantidad - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        cantidad--;
    }

    public int Tope() {
        return elementos[0];
    }

    public boolean PilaVacia() {
        return cantidad == 0;
    }
}
