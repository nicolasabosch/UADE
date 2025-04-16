num=int(input("Decime un numero para hacer el factorial: "))
factorial=num

for i in range(factorial-1,0,-1):
    factorial*=i
print("El factorial de", num,"es",factorial)