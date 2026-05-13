public class MainEJ6 {
    public static void main(String[] args) {
        Cola c= new Cola(6);
        Cola colaAuxiliar= new Cola(6);
        DiccionarioSimple d= new DiccionarioSimple();

        c.enqueue(7);
        c.enqueue(2);
        c.enqueue(7);
        c.enqueue(5);
        c.enqueue(2);
        c.enqueue(7);
        int i=1;
        while (!c.isEmpty()) {
            int x= c.front();
            c.dequeue();
            colaAuxiliar.enqueue(x);

            if (!d.containsKey(x)) {
                d.put(i, x);
                
            }

            i++;
            
        }
        while (!colaAuxiliar.isEmpty()) {
            int x= colaAuxiliar.front();
            colaAuxiliar.dequeue();
            c.enqueue(x);
            
        }
        //return d




    }
}
