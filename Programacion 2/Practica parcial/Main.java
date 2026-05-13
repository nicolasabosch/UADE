public class Main {
    public static void main(String[] args) {

        //todosDistintos();
        pilaDiccionario();
    }

    public static Boolean todosDistintos() {
        Pila p = new Pila(4);
        Pila pAux = new Pila(4);
        Conjunto c = new Conjunto(4);
        p.push(6);
        p.push(1);
        p.push(4);
        p.push(1);
        while (!p.isEmpty()) {
            int x = p.peek();
            p.pop();
            pAux.push(x);

            if (!c.contains(x)) {
                c.add(x);
            } else {
                System.out.println("False");
                return false;
            }
        }

        while (!pAux.isEmpty()) {
            int x= pAux.peek();
            pAux.pop();
            p.push(x);

            
        }

        System.out.println("True");
        return true;

    }

    public static DiccionarioSimple pilaDiccionario() {
        Pila p= new Pila(6);
        Pila pAux= new Pila(6);
        DiccionarioSimple d= new DiccionarioSimple();

        p.push(4);
        p.push(7);
        p.push(4);
        p.push(2);
        p.push(7);
        p.push(7);

        while (!p.isEmpty()) {
            int x= p.peek();
            p.pop();
            pAux.push(x);
            
            if(!d.containsKey(x)) {
                d.put(x, 1);
            }
            else {
                d.put(x, d.get(x)+1);
            }
            
        }
        return d;

    }
}
