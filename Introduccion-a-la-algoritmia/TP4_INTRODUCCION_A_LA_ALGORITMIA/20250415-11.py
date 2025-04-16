h=int(input("Decime un numero y te digo si es primo: "))
divisible=False
for i in range(h-1,1,-1):
    if h%i==0:
        divisible=True
        break
    
if divisible==True:
    print("El numero", h,"no es primo")
else:
    print("El numero", h,"es primo")
    
        