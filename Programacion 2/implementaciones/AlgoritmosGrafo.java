public class AlgoritmosGrafo {
    public static ConjuntoTDA vecinosSalientes(GrafoTDA g, int origen) {
        ConjuntoTDA vecinos = new ConjuntoLD();
        vecinos.InicializarConjunto();
        ConjuntoTDA vertices = g.Vertices();

        while (!vertices.ConjuntoVacio()) {
            int destino = vertices.Elegir();
            vertices.Sacar(destino);
            if (g.ExisteArista(origen, destino)) {
                vecinos.Agregar(destino);
            }
        }
        return vecinos;
    }

    public static int gradoPositivo(GrafoTDA g, int v) {
        int contador = 0;
        ConjuntoTDA vertices = g.Vertices();
        while (!vertices.ConjuntoVacio()) {
            int w = vertices.Elegir();
            vertices.Sacar(w);
            if (g.ExisteArista(v, w)) {
                contador++;
            }
        }
        return contador;
    }

    public static int gradoNegativo(GrafoTDA g, int v) {
        int contador = 0;
        ConjuntoTDA vertices = g.Vertices();
        while (!vertices.ConjuntoVacio()) {
            int w = vertices.Elegir();
            vertices.Sacar(w);
            if (g.ExisteArista(w, v)) {
                contador++;
            }
        }
        return contador;
    }

    public static boolean esAislado(GrafoTDA g, int v) {
        return gradoPositivo(g, v) == 0 && gradoNegativo(g, v) == 0;
    }

    public static ConjuntoTDA bfs(GrafoTDA g, int origen) {
        ConjuntoTDA visitados = new ConjuntoLD();
        visitados.InicializarConjunto();

        ColaTDA pendientes = new ColaLD();
        pendientes.InicializarCola();

        visitados.Agregar(origen);
        pendientes.Acolar(origen);

        while (!pendientes.ColaVacia()) {
            int actual = pendientes.Primero();
            pendientes.Desacolar();

            ConjuntoTDA vertices = g.Vertices();
            while (!vertices.ConjuntoVacio()) {
                int vecino = vertices.Elegir();
                vertices.Sacar(vecino);
                if (g.ExisteArista(actual, vecino) && !visitados.Pertenece(vecino)) {
                    visitados.Agregar(vecino);
                    pendientes.Acolar(vecino);
                }
            }
        }

        return visitados;
    }

    public static boolean existeCaminoBFS(GrafoTDA g, int origen, int destino) {
        ConjuntoTDA visitados = bfs(g, origen);
        return visitados.Pertenece(destino);
    }

    public static void dfs(GrafoTDA g, int actual, ConjuntoTDA visitados) {
        visitados.Agregar(actual);

        ConjuntoTDA vertices = g.Vertices();
        while (!vertices.ConjuntoVacio()) {
            int vecino = vertices.Elegir();
            vertices.Sacar(vecino);
            if (g.ExisteArista(actual, vecino) && !visitados.Pertenece(vecino)) {
                dfs(g, vecino, visitados);
            }
        }
    }

    public static boolean existeCaminoDFS(GrafoTDA g, int origen, int destino) {
        ConjuntoTDA visitados = new ConjuntoLD();
        visitados.InicializarConjunto();
        dfs(g, origen, visitados);
        return visitados.Pertenece(destino);
    }

    public static ResultadoDijkstra dijkstra(GrafoTDA g, int origen) {
        int inf = 999999;
        ConjuntoTDA conjVertices = g.Vertices();
        int n = contar(conjVertices);
        int[] vertices = pasarAArreglo(g.Vertices(), n);
        int[] distancia = new int[n];
        int[] anterior = new int[n];
        boolean[] visitado = new boolean[n];

        for (int i = 0; i < n; i++) {
            distancia[i] = inf;
            anterior[i] = -1;
            visitado[i] = false;
        }

        int posOrigen = indiceDe(vertices, n, origen);
        distancia[posOrigen] = 0;

        for (int i = 0; i < n; i++) {
            int posActual = menorNoVisitado(distancia, visitado, n);
            if (posActual == -1) {
                return new ResultadoDijkstra(vertices, distancia, anterior);
            }

            visitado[posActual] = true;
            int actual = vertices[posActual];

            for (int j = 0; j < n; j++) {
                int vecino = vertices[j];
                if (!visitado[j] && g.ExisteArista(actual, vecino)) {
                    int nuevoCosto = distancia[posActual] + g.PesoArista(actual, vecino);
                    if (nuevoCosto < distancia[j]) {
                        distancia[j] = nuevoCosto;
                        anterior[j] = actual;
                    }
                }
            }
        }

        return new ResultadoDijkstra(vertices, distancia, anterior);
    }

    public static int menorNoVisitado(int[] distancia, boolean[] visitado, int n) {
        int posMenor = -1;
        int menor = 999999;

        for (int i = 0; i < n; i++) {
            if (!visitado[i] && distancia[i] < menor) {
                menor = distancia[i];
                posMenor = i;
            }
        }

        return posMenor;
    }

    private static int contar(ConjuntoTDA conjunto) {
        int total = 0;
        while (!conjunto.ConjuntoVacio()) {
            int elegido = conjunto.Elegir();
            conjunto.Sacar(elegido);
            total++;
        }
        return total;
    }

    private static int[] pasarAArreglo(ConjuntoTDA conjunto, int n) {
        int[] arreglo = new int[n];
        int i = 0;
        while (!conjunto.ConjuntoVacio()) {
            int elegido = conjunto.Elegir();
            conjunto.Sacar(elegido);
            arreglo[i] = elegido;
            i++;
        }
        return arreglo;
    }

    private static int indiceDe(int[] vertices, int n, int vertice) {
        int i = 0;
        while (i < n && vertices[i] != vertice) {
            i++;
        }
        return i;
    }

    public static class ResultadoDijkstra {
        public final int[] vertices;
        public final int[] distancia;
        public final int[] anterior;

        ResultadoDijkstra(int[] vertices, int[] distancia, int[] anterior) {
            this.vertices = vertices;
            this.distancia = distancia;
            this.anterior = anterior;
        }
    }
}
