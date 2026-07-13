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
