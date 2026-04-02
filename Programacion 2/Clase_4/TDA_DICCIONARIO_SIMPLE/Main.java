public class Main {
    public static void main(String[] args) {
    
    DiccionarioSimple dic = new DiccionarioSimple();
    
    dic.put(3,95);
    dic.put(8, 21);
    dic.put(3, 7); // reemplaza
    
    System.out.println(dic.get(3)); // imprime 7
    
    dic.remove(8);
    
    System.out.println(dic.containsKey(8)); // false
    
    }
}