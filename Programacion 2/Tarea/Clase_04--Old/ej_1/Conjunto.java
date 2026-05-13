public class Conjunto {
    int cantidad = 0;
    int[] sinElementosDuplicados;

    public int count(int[] elementos) {
        int[] temp = new int[elementos.length];
        int k = 0;

        for (int i = 0; i < elementos.length; i++) {
            boolean yaEstaba = false;
            for (int j = 0; j < k; j++) {
                if (temp[j] == elementos[i]) {
                    yaEstaba = true;
                    break;
                }
            }
            if (!yaEstaba) {
                temp[k] = elementos[i];
                k++;
            }
        }
        cantidad = k;
        sinElementosDuplicados = new int[k];
        for (int i = 0; i < k; i++) {
            sinElementosDuplicados[i] = temp[i];
        }
        return cantidad;
    }


}