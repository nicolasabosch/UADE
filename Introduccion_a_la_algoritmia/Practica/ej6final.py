import random
num=11
secuencia=[]
listaConCeros=[]
suma=0
intervalos=[]
numerosEnIntervalos=[]
sumaIntervalo=0
for i in range (num):
    nuevoNumero=random.randint(1,20)
    secuencia.append(nuevoNumero)

i=0
while i<len(secuencia):
    suma+=secuencia[i]
    if suma >20:
        listaConCeros.append(0)
        listaConCeros.append(secuencia[i])
        suma=secuencia[i]
        i+=1
    else:
        listaConCeros.append(secuencia[i])
        i+=1
if i==11:
    listaConCeros.append(0)

print(listaConCeros)

def buscarIntervalos(listaConCeros, sumaIntervalo):
    listaAuxiliar=[]

    for i in range (len(listaConCeros)):
        if listaConCeros[i]==0:
            intervalos.append(sumaIntervalo)
            sumaIntervalo=0
            numerosEnIntervalos.append(listaAuxiliar)
            listaAuxiliar=[]
        elif listaConCeros[i]!=0:
            sumaIntervalo+=1
            listaAuxiliar.append(listaConCeros[i])

buscarIntervalos(listaConCeros, sumaIntervalo)

def buscarIntervaloMasLargo():
    intervaloMasLargo=0
    for i in range(len(intervalos)):
        if intervalos[i]>intervaloMasLargo:
            intervaloMasLargo=intervalos[i]
    return intervaloMasLargo

intervaloMasLargo=buscarIntervaloMasLargo()

def imprimirIntervalosMasLargos(intervaloMasLargo):
    for i in range (len(numerosEnIntervalos)):
        

        if len(numerosEnIntervalos[i])== intervaloMasLargo:
            print(numerosEnIntervalos[i])

imprimirIntervalosMasLargos(intervaloMasLargo)

