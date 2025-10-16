lista=[1,3,4,7,9,12,16,19,21,22]
izq=0
der=len(lista)-1
numero=3
#numero=3
def busquedaBinaria(lista, izq, der, numero):
    medio= izq+der//2
    encontrado=False
    while lista[medio]!=numero and izq<=der and encontrado==False:
        
        medio= (izq+der)//2
        
        if lista[medio]==numero:
            print("Felicitaciones encontraste el numero en la posicion", medio, "de la lista")
            encontrado=True
        
        elif lista[medio]>numero:
            der=medio-1

        elif lista[medio] <numero:
            izq=medio+1

    if encontrado==False:
        print("Numero no encontrado, ingrese un numero valido")
    