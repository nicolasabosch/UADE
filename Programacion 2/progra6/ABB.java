package progra6;

public class ABB implements ABBTDA {
	 private NodoABB raiz;

	    @Override
	    public void InicializarArbol() {
	        raiz = null;         
	    }

	    @Override
	    public boolean ArbolVacio() {
	        return raiz == null;
	    }

	    @Override
	    public int Raiz() {
	        return raiz.info;
	    }

	    @Override
	    public ABBTDA HijoIzq() {
	        return raiz.hijoIzq;
	    }

	    @Override
	    public ABBTDA HijoDer() {
	        return raiz.hijoDer;
	    }

	    @Override
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

	    @Override
	    public void EliminarElem(int x) {
	        if (raiz != null) {
	            if (x < raiz.info) {
	                raiz.hijoIzq.EliminarElem(x);
	            } else if (x > raiz.info) {
	                raiz.hijoDer.EliminarElem(x);
	            } else if (raiz.hijoIzq.ArbolVacio() && raiz.hijoDer.ArbolVacio()) {
	                raiz = null;                              
	            } else if (raiz.hijoIzq.ArbolVacio()) {
	                raiz = ((ABB) raiz.hijoDer).raiz;         
	            } else if (raiz.hijoDer.ArbolVacio()) {
	                raiz = ((ABB) raiz.hijoIzq).raiz;         
	            } else {
	                
	                raiz.info = mayor(raiz.hijoIzq);
	                raiz.hijoIzq.EliminarElem(raiz.info);
	            }
	        }
	    }

	    private int mayor(ABBTDA a) {
	        if (a.HijoDer().ArbolVacio()) {
	            return a.Raiz();
	        } else {
	            return mayor(a.HijoDer());
	        }
	    }
	}

	


