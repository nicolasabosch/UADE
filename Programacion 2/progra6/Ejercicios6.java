package progra6;

public class Ejercicios6 {
	public static void main(String[] args) {
        ABBTDA arbol = new ABB();
        arbol.InicializarArbol();

        arbol.AgregarElem(10);
        arbol.AgregarElem(5);
        arbol.AgregarElem(15);
        arbol.AgregarElem(3);
        arbol.AgregarElem(7);

        System.out.println("Raíz: " + arbol.Raiz());              
        System.out.println("Hijo izq: " + arbol.HijoIzq().Raiz()); 
        System.out.println("Hijo der: " + arbol.HijoDer().Raiz()); 

        arbol.EliminarElem(10);
        System.out.println("Raíz tras eliminar 10: " + arbol.Raiz()); 
    }
	
}
	

