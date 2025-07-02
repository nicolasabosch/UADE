legajos=[1234, 5678, 9012, 3456, 1010, 1111]
notas=[3, 2, 4, 8, 9, 10]
aprobados=0
desaprobados=0
legajosSuperan=[]
promedio=0
matrizNotas=[]

def cargarLegajosYNotas(legajos, notas):
    cargarLegajo=True
    while cargarLegajo:
        
        legajo= int(input("Ingrese el numero de legajo, ingresa -1 para salir del programa: "))
        if legajo!=-1:
            legajos.append(legajo)

        else:
            cargarLegajo=False
        
        if cargarLegajo==True:
            nota= int(input("Ingrese una nota entre 1 y 10: "))
            while nota <1 or nota >10:
                nota= int(input("Ingrese una nota valida entre 1 y 10: "))
        
            notas.append(nota)

def aprobacion(notas, aprobados, desaprobados):

    for i in range(len(notas)):

        if notas[i]>=4:
            aprobados+=1
        
        else:
            desaprobados+=1

    print("El examen lo aprobaron ", aprobados, "personas")
    print("El examen lo desaaprobaron ", desaprobados, "personas")

def promedioNotas(notas, promedio):
    for i in range (len(notas)):
        promedio+=notas[i]
    promedio=promedio//(len(notas))
    
    print("El promedio es de: ", promedio)

    for i in range(len(legajos)):
        if notas[i]>promedio:
            legajosSuperan.append(legajos[i])
    print("Los legajos que superan son: ", legajosSuperan)

def ordenarLegajos(legajos, notas):
    for i in range(1, len(legajos)):
        aux=legajos[i]
        aux2=notas[i]
        j=i
        while j>0 and legajos[j-1] > aux:
            legajos[j]= legajos[j-1]
            notas[j]= notas[j-1]

            j=j-1
        legajos[j]=aux
        notas[j]=aux2
    
    
    for z in range(len(legajos)):
        matrizNotas.append([legajos[z],notas[z]])
    
    print(legajos)
    print(notas)
    print(matrizNotas)


cargarLegajosYNotas(legajos, notas)
aprobacion(notas, aprobados, desaprobados)
promedioNotas(notas, promedio)
ordenarLegajos(legajos, notas)


    

