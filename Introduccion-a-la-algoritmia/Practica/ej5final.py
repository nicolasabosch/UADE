import random

print(4321.0//10.0)

def numeroToArray(numero):
    lista_digitos=[]
    while numero > 0:
        digito = numero % 10
        lista_digitos.append(digito)
        numero //= 10

    lista=[]
    for i in range(len(lista_digitos)-1,-1,-1,):
        lista.append(lista_digitos[i])        
    return lista

rnd=random.randint(1000,9999)
numeroMaquina =numeroToArray(rnd)

numeroUsuario=[1,2,3,4]
digitosEnPosicion=0
digitasFueraDePosicion=0
for i in range(len(numeroUsuario)):
    if numeroUsuario[i]==numeroMaquina[i]:
        digitosEnPosicion+=1
    else:
        for j in range(len(numeroMaquina)):
            if numeroUsuario[i]==numeroMaquina[j]:
                digitasFueraDePosicion+=1

print(numeroMaquina)
print(numeroUsuario)
print("En posicion: ",digitosEnPosicion)
print("Fuera de posicion: ",digitasFueraDePosicion)

