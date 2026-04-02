class MainColaPrioridadObj {

    public static void main(String[] args) {
    
    ColaPrioridadObj cola = new ColaPrioridadObj(10);
    
    cola.acolarPrioridad(8, 1);
    cola.acolarPrioridad(95, 3);
    cola.acolarPrioridad(7, 3);
    cola.acolarPrioridad(15, 4);
    cola.acolarPrioridad(21, 5);
    cola.acolarPrioridad(8, 7);
    cola.acolarPrioridad(8, 8);
    cola.acolarPrioridad(7, 8);
    
    // insertamos el que querías probar
    cola.acolarPrioridad(16, 5);
    
    System.out.println("Orden de salida por prioridad:\n");
    
    while (!cola.isEmpty()) {
    System.out.println(
    "Elemento: " + cola.primero() +
    " | Prioridad: " + cola.prioridad()
    
);
    cola.desacolar();
    
    
    }
}
}