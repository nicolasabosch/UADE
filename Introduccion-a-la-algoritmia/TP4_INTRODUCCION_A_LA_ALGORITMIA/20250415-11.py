h=int(input("Decime un numero y te digo si es primo: "))
divisible=False
i = h - 1
while i > 1:
    if h % i == 0:
        divisible = True
        break
    i -= 1
if divisible==True:
    print("El numero", h,"no es primo")
else:
    print("El numero", h,"es primo")
    
        