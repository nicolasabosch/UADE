num=int(input("Decime con que numero termina la patente: "))
patentePar=0
patenteImpar=0

while num!=-1:
    num=int(input("Decime con que numero termina la patente: "))
    if num%2==0:
        patentePar+=1
    else:
        patenteImpar+=1

print("Pasaron", patentePar ,"autos con patente par y", patenteImpar ,"autos con patente impar")