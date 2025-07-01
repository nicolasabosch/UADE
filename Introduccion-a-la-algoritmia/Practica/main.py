matriz=[]
categorias=[]
estadistica=[]
chino=0
totalChino=0
argento=0
totalArgento=0
precios = [250, 1000, 500, 1500, 2000, 3000, 4000]
alimentosCategoria = ["chino", "chino", "chino", "argento", "argento", "argento", "argento",]
idAlimentos = [1,2,3,4,5,6,7]
for i in range(len(precios)):
    matriz.append([])

for i in range(len(precios)):
    matriz[i].append(idAlimentos[i])
    matriz[i].append(alimentosCategoria[i])
    matriz[i].append(precios[i])

for i in range(len(matriz)):
    if matriz[i][1]=="chino":
        chino+=1
        totalChino+=precios[i]
    elif matriz[i][1]=="argento":
        argento+=1
        totalArgento+=precios[i]
    


for j in range(len(alimentosCategoria)):
    if len(categorias) <=0:
        categorias.append(alimentosCategoria[j])
    
    elif len(categorias)>=1:
        h=0
        buscando=True
        while buscando and h<=len(categorias) and categorias[h]!=alimentosCategoria[j]:
            if categorias[h] == alimentosCategoria[j]:
                h+=1
                
            elif categorias[h] != alimentosCategoria[j]:
                repetido=0
                for categoria in range(len(categorias)):
                    if categorias[categoria]==alimentosCategoria[j]:
                        repetido+=1
                        

                buscando=False
                
        if buscando==False and repetido==0:
            categorias.append(alimentosCategoria[j])


# for z in range(len(categorias) +1):
#     estadistica.append([])


# for h in range(len(categorias)):
#     if matriz[h][1]=="chino":
#         estadistica.append([1,"chino", totalChino])

#     elif matriz[h][1]=="argento":
#         argento+=1
#         estadistica.append([2,"argento", totalArgento])






print(estadistica)