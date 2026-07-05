# Implementaciones de TDA extraidas de las clases 7 a 13

Fuente: PDFs indicados por el usuario. Formatee el texto y corregi espacios/errores de extraccion cuando el PDF venia como imagen o con OCR imperfecto.

## Clase 7 - Implementaciones dinamicas y costos

### PilaTDA

Operaciones: `InicializarPila`, `Apilar`, `Desapilar`, `Tope`, `PilaVacia`.

Implementaciones vistas:

| Implementacion | Representacion interna | Costos principales |
|---|---|---|
| `PilaTI` | Arreglo con tope al inicio | `Apilar` y `Desapilar` lineales por corrimientos |
| `PilaTF` | Arreglo con tope al final e indice | Todas las operaciones basicas constantes |
| `PilaLD` | Nodos enlazados, el primer nodo es el tope | Todas las operaciones basicas constantes |

Idea de `PilaLD`:

```java
class NodoPila {
    int info;
    NodoPila sig;
}

class PilaLD {
    NodoPila primero;
}
```

### ColaTDA

Operaciones: `InicializarCola`, `Acolar`, `Desacolar`, `Primero`, `ColaVacia`.

Implementaciones vistas:

| Implementacion | Representacion interna | Costos principales |
|---|---|---|
| `ColaPU` | Arreglo, ultimo ingresado en posicion inicial | `Acolar` lineal, `Desacolar` constante |
| `ColaPI` | Arreglo, primer ingresado en posicion inicial | `Acolar` constante, `Desacolar` lineal |
| `ColaLD` | Nodos enlazados con referencia a primero y ultimo | Todas las operaciones basicas constantes |

Idea de `ColaLD`:

```java
class NodoCola {
    int info;
    NodoCola sig;
}

class ColaLD {
    NodoCola primero;
    NodoCola ultimo;
}
```

### ColaPrioridadTDA

Operaciones: `InicializarCola`, `AcolarPrioridad`, `Desacolar`, `Primero`, `ColaVacia`, `Prioridad`.

Implementaciones vistas:

| Implementacion | Representacion interna | Costos principales |
|---|---|---|
| `ColaPrioridadDA` | Dos arreglos paralelos: valores y prioridades | `AcolarPrioridad` lineal |
| `ColaPrioridadAO` | Arreglo de objetos/elementos compuestos | `AcolarPrioridad` lineal |
| `ColaPrioridadLD` | Nodos con valor, prioridad y siguiente | `AcolarPrioridad` lineal |

Idea de `ColaPrioridadLD`:

```java
class NodoPrioridad {
    int info;
    int prioridad;
    NodoPrioridad sig;
}
```

La lista se mantiene ordenada por prioridad. La insercion recorre hasta encontrar la posicion correcta; por eso sigue siendo lineal.

### ConjuntoTDA

Operaciones: `InicializarConjunto`, `Agregar`, `Sacar`, `Elegir`, `Pertenece`, `ConjuntoVacio`.

Implementaciones vistas:

| Implementacion | Representacion interna | Costos principales |
|---|---|---|
| `ConjuntoA` | Arreglo sin orden y cantidad | `Agregar`, `Sacar`, `Pertenece` lineales |
| `ConjuntoLD` | Lista de nodos enlazados | `Agregar`, `Sacar`, `Pertenece` lineales |

Idea:

```java
class NodoConjunto {
    int info;
    NodoConjunto sig;
}
```

### DiccionarioSimpleTDA

Operaciones: `InicializarDiccionario`, `Agregar`, `Eliminar`, `Recuperar`, `Claves`.

Implementaciones vistas:

| Implementacion | Representacion interna | Costos principales |
|---|---|---|
| `DicSimpleA` | Arreglo de elementos clave-valor | `Agregar`, `Eliminar`, `Recuperar`, `Claves` lineales |
| `DicSimpleL` | Lista de nodos clave-valor | `Agregar`, `Eliminar`, `Recuperar`, `Claves` lineales |

Idea:

```java
class NodoDicSimple {
    int clave;
    int valor;
    NodoDicSimple sig;
}
```

### DiccionarioMultipleTDA

Operaciones: `InicializarDiccionario`, `Agregar`, `Eliminar`, `EliminarValor`, `Recuperar`, `Claves`.

Implementaciones vistas:

| Implementacion | Representacion interna | Costos principales |
|---|---|---|
| `DicMultipleA` | Arreglo de claves, cada clave con arreglo de valores | Operaciones principales lineales |
| `DicMultipleL` | Lista de nodos clave y, por cada clave, lista de valores | Operaciones principales lineales |

Idea:

```java
class NodoValor {
    int valor;
    NodoValor sigValor;
}

class NodoClave {
    int clave;
    NodoValor valores;
    NodoClave sigClave;
}
```

## Clase 8 - Arboles y ABB

### TDA Arbol general

```java
public interface ANaTDA {
    int Valor();
    ANaTDA HijoMayor();
    ANaTDA HermanoSig();
    boolean ArbolVacio();
    void InicializarArbol();
    void ElimHijoMConDesc(int x);
    void ElimHermSConDesc(int x);
    void CrearArbol(int r);
    void AgregarHijoM(int p, int h);
}
```

### TDA ABB

```java
public interface ABBTDA {
    int Raiz();
    ABBTDA HijoIzq();
    ABBTDA HijoDer();
    boolean ArbolVacio();
    void InicializarArbol();
    void AgregarElem(int x);
    void EliminarElem(int x);
}
```

### Implementacion basica de ABB con nodos dinamicos

```java
class NodoABB {
    int info;
    ABBTDA hijoIzq;
    ABBTDA hijoDer;
}

public class ABB implements ABBTDA {
    NodoABB raiz;

    public void InicializarArbol() {
        raiz = null;
    }

    public boolean ArbolVacio() {
        return raiz == null;
    }

    public int Raiz() {
        return raiz.info;
    }

    public ABBTDA HijoIzq() {
        return raiz.hijoIzq;
    }

    public ABBTDA HijoDer() {
        return raiz.hijoDer;
    }

    public void AgregarElem(int x) {
        if (raiz == null) {
            raiz = new NodoABB();
            raiz.info = x;
            raiz.hijoIzq = new ABB();
            raiz.hijoIzq.InicializarArbol();
            raiz.hijoDer = new ABB();
            raiz.hijoDer.InicializarArbol();
        } else if (x < raiz.info) {
            raiz.hijoIzq.AgregarElem(x);
        } else if (x > raiz.info) {
            raiz.hijoDer.AgregarElem(x);
        }
    }
}
```

### Recorridos de ABB

```java
public void preOrder(ABBTDA a) {
    if (!a.ArbolVacio()) {
        System.out.println(a.Raiz());
        preOrder(a.HijoIzq());
        preOrder(a.HijoDer());
    }
}

public void inOrder(ABBTDA a) {
    if (!a.ArbolVacio()) {
        inOrder(a.HijoIzq());
        System.out.println(a.Raiz());
        inOrder(a.HijoDer());
    }
}

public void postOrder(ABBTDA a) {
    if (!a.ArbolVacio()) {
        postOrder(a.HijoIzq());
        postOrder(a.HijoDer());
        System.out.println(a.Raiz());
    }
}
```

### Busqueda en ABB

```java
public boolean existe(ABBTDA a, int x) {
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
```

## Clase 9 - Problemas sobre ABB

```java
public static int contarNodos(ABBTDA a) {
    if (a.ArbolVacio()) {
        return 0;
    } else {
        return 1 + contarNodos(a.HijoIzq()) + contarNodos(a.HijoDer());
    }
}

public static int contarHojas(ABBTDA a) {
    if (a.ArbolVacio()) {
        return 0;
    } else if (a.HijoIzq().ArbolVacio() && a.HijoDer().ArbolVacio()) {
        return 1;
    } else {
        return contarHojas(a.HijoIzq()) + contarHojas(a.HijoDer());
    }
}

public static int contarInternos(ABBTDA a) {
    if (a.ArbolVacio()) {
        return 0;
    } else if (a.HijoIzq().ArbolVacio() && a.HijoDer().ArbolVacio()) {
        return 0;
    } else {
        return 1 + contarInternos(a.HijoIzq()) + contarInternos(a.HijoDer());
    }
}

public static int altura(ABBTDA a) {
    if (a.ArbolVacio()) {
        return -1;
    } else {
        int alturaIzq = altura(a.HijoIzq());
        int alturaDer = altura(a.HijoDer());
        return 1 + Math.max(alturaIzq, alturaDer);
    }
}
```

```java
public static int profundidad(ABBTDA a, int x) {
    if (a.ArbolVacio()) {
        return -1;
    } else if (a.Raiz() == x) {
        return 0;
    } else if (x < a.Raiz()) {
        int p = profundidad(a.HijoIzq(), x);
        if (p == -1) {
            return -1;
        } else {
            return 1 + p;
        }
    } else {
        int p = profundidad(a.HijoDer(), x);
        if (p == -1) {
            return -1;
        } else {
            return 1 + p;
        }
    }
}

public static int menor(ABBTDA a) {
    if (a.HijoIzq().ArbolVacio()) {
        return a.Raiz();
    } else {
        return menor(a.HijoIzq());
    }
}

public static int mayor(ABBTDA a) {
    if (a.HijoDer().ArbolVacio()) {
        return a.Raiz();
    } else {
        return mayor(a.HijoDer());
    }
}
```

```java
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
    } else {
        return a.Raiz() + sumar(a.HijoIzq()) + sumar(a.HijoDer());
    }
}

public static int sumarMayoresQue(ABBTDA a, int x) {
    if (a.ArbolVacio()) {
        return 0;
    } else if (a.Raiz() <= x) {
        return sumarMayoresQue(a.HijoDer(), x);
    } else {
        return a.Raiz()
                + sumarMayoresQue(a.HijoIzq(), x)
                + sumarMayoresQue(a.HijoDer(), x);
    }
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
```

```java
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
```

## Clase 10 - AVL

### Nodo AVL y auxiliares

```java
class NodoAVL {
    int valor;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;

    NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
        this.izquierdo = null;
        this.derecho = null;
    }
}

int altura(NodoAVL nodo) {
    if (nodo == null) {
        return 0;
    }
    return nodo.altura;
}

int factorEquilibrio(NodoAVL nodo) {
    if (nodo == null) {
        return 0;
    }
    return altura(nodo.derecho) - altura(nodo.izquierdo);
}

void actualizarAltura(NodoAVL nodo) {
    nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
}
```

### Rotaciones AVL

```java
NodoAVL rotarDerecha(NodoAVL y) {
    NodoAVL x = y.izquierdo;
    NodoAVL t2 = x.derecho;

    x.derecho = y;
    y.izquierdo = t2;

    actualizarAltura(y);
    actualizarAltura(x);

    return x;
}

NodoAVL rotarIzquierda(NodoAVL x) {
    NodoAVL y = x.derecho;
    NodoAVL t2 = y.izquierdo;

    y.izquierdo = x;
    x.derecho = t2;

    actualizarAltura(x);
    actualizarAltura(y);

    return y;
}
```

### Insercion balanceada en AVL

```java
NodoAVL insertar(NodoAVL nodo, int valor) {
    if (nodo == null) {
        return new NodoAVL(valor);
    }

    if (valor < nodo.valor) {
        nodo.izquierdo = insertar(nodo.izquierdo, valor);
    } else if (valor > nodo.valor) {
        nodo.derecho = insertar(nodo.derecho, valor);
    } else {
        return nodo; // no se insertan repetidos
    }

    actualizarAltura(nodo);
    int fe = factorEquilibrio(nodo);

    if (fe < -1 && valor < nodo.izquierdo.valor) {
        return rotarDerecha(nodo);
    }

    if (fe > 1 && valor > nodo.derecho.valor) {
        return rotarIzquierda(nodo);
    }

    if (fe < -1 && valor > nodo.izquierdo.valor) {
        nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
        return rotarDerecha(nodo);
    }

    if (fe > 1 && valor < nodo.derecho.valor) {
        nodo.derecho = rotarDerecha(nodo.derecho);
        return rotarIzquierda(nodo);
    }

    return nodo;
}
```

## Clase 11 - Arbol B

Representacion conceptual de nodo B:

```java
class NodoB {
    int[] claves;
    NodoB[] hijos;
    int cantidadClaves;
    boolean hoja;
}
```

Reglas de implementacion extraidas:

- Un nodo contiene varias claves ordenadas.
- El arreglo `hijos` contiene multiples referencias, no solo izquierda/derecha.
- `cantidadClaves` indica cuantas posiciones reales estan ocupadas.
- `hoja` indica si el nodo esta en el nivel final.
- Insercion: buscar la hoja, insertar en orden, dividir si supera capacidad, promocionar la clave central al padre.
- Eliminacion: puede requerir prestamo/redistribucion o fusion por subocupacion.

## Clase 12 - GrafoTDA

### Especificacion

```java
public interface GrafoTDA {
    void InicializarGrafo();
    void AgregarVertice(int v);
    void EliminarVertice(int v);
    ConjuntoTDA Vertices();
    void AgregarArista(int v1, int v2, int peso);
    void EliminarArista(int v1, int v2);
    boolean ExisteArista(int v1, int v2);
    int PesoArista(int v1, int v2);
}
```

### Implementacion con matriz de adyacencia

```java
public class GrafoMA implements GrafoTDA {
    static int n = 100;
    int[][] MAdy;
    int[] Etiqs;
    int cantNodos;

    public void InicializarGrafo() {
        MAdy = new int[n][n];
        Etiqs = new int[n];
        cantNodos = 0;
    }

    public void AgregarVertice(int v) {
        Etiqs[cantNodos] = v;
        for (int i = 0; i <= cantNodos; i++) {
            MAdy[cantNodos][i] = 0;
            MAdy[i][cantNodos] = 0;
        }
        cantNodos++;
    }

    public void EliminarVertice(int v) {
        int ind = Vert2Indice(v);
        for (int k = 0; k < cantNodos; k++) {
            MAdy[k][ind] = MAdy[k][cantNodos - 1];
        }
        for (int k = 0; k < cantNodos; k++) {
            MAdy[ind][k] = MAdy[cantNodos - 1][k];
        }
        Etiqs[ind] = Etiqs[cantNodos - 1];
        cantNodos--;
    }

    private int Vert2Indice(int v) {
        int i = cantNodos - 1;
        while (i >= 0 && Etiqs[i] != v) {
            i--;
        }
        return i;
    }

    public ConjuntoTDA Vertices() {
        ConjuntoTDA Vert = new ConjuntoLD();
        Vert.InicializarConjunto();
        for (int i = 0; i < cantNodos; i++) {
            Vert.Agregar(Etiqs[i]);
        }
        return Vert;
    }

    public void AgregarArista(int v1, int v2, int peso) {
        int o = Vert2Indice(v1);
        int d = Vert2Indice(v2);
        MAdy[o][d] = peso;
    }

    public void EliminarArista(int v1, int v2) {
        int o = Vert2Indice(v1);
        int d = Vert2Indice(v2);
        MAdy[o][d] = 0;
    }

    public boolean ExisteArista(int v1, int v2) {
        int o = Vert2Indice(v1);
        int d = Vert2Indice(v2);
        return MAdy[o][d] != 0;
    }

    public int PesoArista(int v1, int v2) {
        int o = Vert2Indice(v1);
        int d = Vert2Indice(v2);
        return MAdy[o][d];
    }
}
```

### Implementacion con lista de adyacencia

```java
class NodoGrafo {
    int nodo;
    NodoArista arista;
    NodoGrafo sigNodo;
}

class NodoArista {
    int etiqueta;
    NodoGrafo nodoDestino;
    NodoArista sigArista;
}
```

```java
public class GrafoLA implements GrafoTDA {
    NodoGrafo origen;

    public void InicializarGrafo() {
        origen = null;
    }

    public void AgregarVertice(int v) {
        NodoGrafo aux = new NodoGrafo();
        aux.nodo = v;
        aux.arista = null;
        aux.sigNodo = origen;
        origen = aux;
    }

    public void AgregarArista(int v1, int v2, int peso) {
        NodoGrafo n1 = Vert2Nodo(v1);
        NodoGrafo n2 = Vert2Nodo(v2);

        NodoArista aux = new NodoArista();
        aux.etiqueta = peso;
        aux.nodoDestino = n2;
        aux.sigArista = n1.arista;
        n1.arista = aux;
    }

    private NodoGrafo Vert2Nodo(int v) {
        NodoGrafo aux = origen;
        while (aux != null && aux.nodo != v) {
            aux = aux.sigNodo;
        }
        return aux;
    }

    public void EliminarVertice(int v) {
        if (origen.nodo == v) {
            origen = origen.sigNodo;
        }

        NodoGrafo aux = origen;
        while (aux != null) {
            EliminarAristaNodo(aux, v);
            if (aux.sigNodo != null && aux.sigNodo.nodo == v) {
                aux.sigNodo = aux.sigNodo.sigNodo;
            }
            aux = aux.sigNodo;
        }
    }

    private void EliminarAristaNodo(NodoGrafo nodo, int v) {
        NodoArista aux = nodo.arista;
        if (aux != null) {
            if (aux.nodoDestino.nodo == v) {
                nodo.arista = aux.sigArista;
            } else {
                while (aux.sigArista != null && aux.sigArista.nodoDestino.nodo != v) {
                    aux = aux.sigArista;
                }
                if (aux.sigArista != null) {
                    aux.sigArista = aux.sigArista.sigArista;
                }
            }
        }
    }

    public ConjuntoTDA Vertices() {
        ConjuntoTDA c = new ConjuntoLD();
        c.InicializarConjunto();
        NodoGrafo aux = origen;
        while (aux != null) {
            c.Agregar(aux.nodo);
            aux = aux.sigNodo;
        }
        return c;
    }

    public void EliminarArista(int v1, int v2) {
        NodoGrafo n1 = Vert2Nodo(v1);
        EliminarAristaNodo(n1, v2);
    }

    public boolean ExisteArista(int v1, int v2) {
        NodoGrafo n1 = Vert2Nodo(v1);
        NodoArista aux = n1.arista;
        while (aux != null && aux.nodoDestino.nodo != v2) {
            aux = aux.sigArista;
        }
        return aux != null;
    }

    public int PesoArista(int v1, int v2) {
        NodoGrafo n1 = Vert2Nodo(v1);
        NodoArista aux = n1.arista;
        while (aux.nodoDestino.nodo != v2) {
            aux = aux.sigArista;
        }
        return aux.etiqueta;
    }
}
```

## Clase 13 - Algoritmos derivados sobre grafos

### Vecinos y grados

```java
public ConjuntoTDA vecinosSalientes(GrafoTDA g, int origen) {
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

public int gradoPositivo(GrafoTDA g, int v) {
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

public int gradoNegativo(GrafoTDA g, int v) {
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

public boolean esAislado(GrafoTDA g, int v) {
    return gradoPositivo(g, v) == 0 && gradoNegativo(g, v) == 0;
}
```

### BFS

```java
public ConjuntoTDA bfs(GrafoTDA g, int origen) {
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

public boolean existeCaminoBFS(GrafoTDA g, int origen, int destino) {
    ConjuntoTDA visitados = bfs(g, origen);
    return visitados.Pertenece(destino);
}
```

### DFS

```java
public void dfs(GrafoTDA g, int actual, ConjuntoTDA visitados) {
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

public boolean existeCaminoDFS(GrafoTDA g, int origen, int destino) {
    ConjuntoTDA visitados = new ConjuntoLD();
    visitados.InicializarConjunto();
    dfs(g, origen, visitados);
    return visitados.Pertenece(destino);
}
```

### Dijkstra didactico con arreglos

```java
public void dijkstra(GrafoTDA g, int origen) {
    int INF = 999999;
    ConjuntoTDA conjVertices = g.Vertices();
    int n = contar(conjVertices);
    int[] vertices = pasarAArreglo(g.Vertices(), n);
    int[] distancia = new int[n];
    int[] anterior = new int[n];
    boolean[] visitado = new boolean[n];

    for (int i = 0; i < n; i++) {
        distancia[i] = INF;
        anterior[i] = -1;
        visitado[i] = false;
    }

    int posOrigen = indiceDe(vertices, n, origen);
    distancia[posOrigen] = 0;

    for (int i = 0; i < n; i++) {
        int posActual = menorNoVisitado(distancia, visitado, n);
        if (posActual == -1) {
            return;
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
}

public int menorNoVisitado(int[] distancia, boolean[] visitado, int n) {
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
```

## Lo mas util para adaptar el TP

- Reemplazar `PriorityQueue` por `ColaPrioridadTDA` propia, preferentemente `ColaPrioridadLD` o arreglo ordenado.
- Reemplazar `HashMap` por `DiccionarioSimpleTDA` propio.
- Reemplazar `HashSet` por `ConjuntoTDA`.
- Reemplazar `Queue`/`LinkedList` por `ColaTDA` propia.
- Para sustitutos, usar `GrafoTDA` con lista de adyacencia y BFS con `ColaTDA` + `ConjuntoTDA`.
- Evitar `stream`, `lambda`, `computeIfAbsent`, `putIfAbsent`, `AtomicInteger`, porque no salen como implementaciones de TDA en estos PDF.
