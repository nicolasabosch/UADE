nTerminos=int(input("Cuantos terminos de la serie de Fibonacci queres ver? "))
fibo=0
fiboAnterior=0   
contador = 0
while contador < nTerminos:
    if fibo < 1:
        for i in range(0, 2, 1):
            fibo += i
            print(fibo)
            contador += 1
            if contador >= nTerminos:
                break
    else:
        if fiboAnterior == 0:
            fiboAnterior = fibo
            print(fiboAnterior)
            contador += 1
            if contador >= nTerminos:
                break
        fiboAnterior += fibo
        print(fiboAnterior)
        contador += 1
        if contador >= nTerminos:
            break
        fibo += fiboAnterior
        print(fibo)
        contador += 1