num=int(input("Decime un numero para hacer el factorial: "))
factorial=num

 
while factorial > 1:
    num *= factorial - 1
    factorial -= 1
print("El factorial de", num,"es",num)