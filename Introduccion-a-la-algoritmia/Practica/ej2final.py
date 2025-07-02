numUnidad=[1,2,3,4,5,6]
superficie=[200, 180, 150, 150, 180, 200]
expensas=[] # $200 por m2
promedioExpensas=0


agregarUnidad=True
while agregarUnidad:
    unidad=int(input("Agrega tu numero de unidad: "))
    i=0
    duplicado=False
    while i < len(numUnidad) and duplicado==False:
        
        if unidad==numUnidad[i]:
            unidad=int(input("El numero de unidad ya existe, ingrese otro numero: "))
        else:
            i+=1
    if duplicado==False:
        numUnidad.append(unidad)
        sup=int(input("Ingrese la superficie de la unidad: "))
        superficie.append(sup)

    agregarUnidad=input("Desea agregar otra unidad? (1.si/2.no): ")
    if agregarUnidad == "1":
        agregarUnidad=True
    else:
        agregarUnidad=False

for i in range(len(numUnidad)):
    expensas.append(superficie[i]*200)

for i in range(len(expensas)):
    print(f"Unidad {numUnidad[i]} tiene una expensa de ${expensas[i]}")
    promedioExpensas+=expensas[i]
promedioExpensas=promedioExpensas//len(expensas)
print("El promedio de expensas es de $",promedioExpensas)

def ordenamientoInsercion(superficie, numUnidad):
    for i in range(len(superficie),1 ):
        aux=superficie[i]
        aux2=numUnidad[i]
        j=i
        while j > 0 and superficie[j-1] > aux:
            superficie[j]= superficie[j-1]
            numUnidad[j] = numUnidad[j-1]
            j=j-1
        superficie[j]=aux
        numUnidad[j]=aux2
    print("Lista ordenada por superficie de mayor a menor:")
    for i in range(len(superficie)):
        print(f"Unidad {numUnidad[i]} con superficie {superficie[i]} m2")

ordenamientoInsercion(superficie, numUnidad)