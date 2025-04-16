nTerminos=int(input("Cuantos terminos de la serie de Fibonacci queres ver? "))
fibo=0
fiboAnterior=0
for p in range (0, nTerminos, 1):
    if fibo<1:
        for i in range(0, 2, 1):
            fibo+=i
            print(fibo)
    
    else:
        if fiboAnterior==0:
            fiboAnterior=fibo
            print(fiboAnterior)
        fiboAnterior+=fibo
        print(fiboAnterior)
        fibo+=fiboAnterior
        print(fibo)    
    