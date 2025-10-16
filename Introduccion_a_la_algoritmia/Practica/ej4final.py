import random

documentos=[12345678, 23456789, 34567890, 45678901, 87654321, 90123456]
numeroIntentos=[7,10,20,5,8,11]
numero=random.randint(1, 100)

intentos=0
adivinado=False
nuevoMaximo=True
while adivinado==False:
    intento=int(input("Adivina el numero, si quieres salir ingresa -1: "))
    if intento> numero:
        print("El numero es menor")
        intentos+=1
    elif intento==-1:
        adivinado=True
        print("No adiviniaste el numero era", numero)
    
    elif intento < numero:
        print("El numero es mayor")
        intentos+=1
    else:
        print("Felicidades encontraste el numero")
        adivinado=True
        intentos+=1
        print("Lo encontraste en", intentos, "intentos")

for i in range(len(documentos)):
    if intentos > numeroIntentos[i]:
       print("Mi intento fue mas lento")
       nuevoMaximo=False

if nuevoMaximo==True:
    documento=int(input("Ingrese el numero de documento para guardar su intento: "))
    documentos.append(documento)
    numeroIntentos.append(intentos)

for i in range(1, len(numeroIntentos)):
    aux=numeroIntentos[i]
    aux2= documentos[i]
    j=i
    while j > 0 and numeroIntentos[j-1] > aux:
        numeroIntentos[j]=numeroIntentos[j-1]
        documentos[j]=documentos[j-1]
        j=j-1
    numeroIntentos[j]= aux
    documentos[j]=aux2

print("Lista ordenada cantidad de intentos ", numeroIntentos)
print("Lista ordenada intentos x documento posicion ",documentos)