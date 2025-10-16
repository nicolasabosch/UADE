
cantDinero=int(input("Ingrese la cantidad de dinero\n"))

billetes1000 = cantDinero//1000
cantDinero=cantDinero%1000

billetes500 = cantDinero//500
cantDinero%=500

billetes100= cantDinero//100
cantDinero%=100

billetes50=cantDinero//50
cantDinero%=50

billetes10=cantDinero//10
cantDinero%=10

billetes5=cantDinero//5
cantDinero%=5

billetes1=cantDinero//1

print("son", billetes1000, "billetes de 1000 \n",billetes500, "billetes de 500:\n",billetes100,"billetes de 100\n", billetes50, "billetes de 50: \n",billetes10, "billetes de 10:\n",billetes5,"billetes de 5 \n", billetes1 ,"billetes de 1")