lista=[7,4,1,9,6,11,3,31]
dato=31

def ordenamientoInsercion(lista):
    for i in range(1, len(lista)):
        aux=lista[i]
        j=i
        while j > 0 and lista[j-1] > aux:
            lista[j]= lista[j-1]
            j=j-1
        lista[j]=aux
    

def busquedaSecuencial(lista, dato):
    i=0
    encontrado=False
    while i <= len(lista)-1 and encontrado==False:
        if lista[i]==dato:
            encontrado=True

        else:
            i+=1
    if encontrado==True:
        print(f"elemento encontrado en la posicion{i}")

izq=0
der=len(lista)-1
numero=3

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
    

def ordenamientoSeleccion(lista):
    for i in range(len(lista)-1):
        for j in range (i+1, len(lista)):
            if lista[i] > lista[j]:
                aux=lista[i]
                lista[i] = lista[j]
                lista[j]=aux
    return lista


def ordenamientoBurbujeo(lista):
    desordenada=True
    while desordenada:
        desordenada=False
        for i in range (len(lista)):
            if lista[i] > lista[i+1]:
                aux=lista[i]
                lista[i]=lista[i+1]
                lista[i+1] =aux
                desordenada = True

legajos= [1234, 5678, 9012, 3456]
edades=[20,24,31,20]

def ordenamientoListasParalelas(legajos, edades):
    for i in range (len(legajos)):
        for j in range (i+1, len(legajos)):
            if edades[i] < edades[j]:
                aux=legajos[i]
                legajos[i]=legajos[j]
                legajos[j]=aux
                aux2=edades[i]
                edades[i]=edades[j]
                edades[j]=aux2