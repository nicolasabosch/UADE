class Program
{
    static void Main()
    {
        int[] numeros = { 7,1,5,3};
        Console.WriteLine("Antes:  " + string.Join(", ", numeros));

        MergeSort(numeros, 0, numeros.Length - 1);

        Console.WriteLine("Después: " + string.Join(", ", numeros));
    }

    static void MergeSort(int[] array, int izquierda, int derecha)
    {
        if (izquierda < derecha)
        {
            int medio = (izquierda + derecha) / 2;

            MergeSort(array, izquierda, medio);
            MergeSort(array, medio + 1, derecha);

            Merge(array, izquierda, medio, derecha);
        }
    }

    static void Merge(int[] array, int izquierda, int medio, int derecha)
    {
        int[] auxiliar = new int[derecha - izquierda + 1];

        int i = izquierda;
        int j = medio + 1;
        int k = 0;

        while (i <= medio && j <= derecha)
        {
            if (array[i] <= array[j])
            {
                auxiliar[k] = array[i];
                i++;
            }
            else
            {
                auxiliar[k] = array[j];
                j++;
            }

            k++;
        }

        while (i <= medio)
        {
            auxiliar[k] = array[i];
            i++;
            k++;
        }

        while (j <= derecha)
        {
            auxiliar[k] = array[j];
            j++;
            k++;
        }

        for (int x = 0; x < auxiliar.Length; x++)
        {
            array[izquierda + x] = auxiliar[x];
        }
    }
}
