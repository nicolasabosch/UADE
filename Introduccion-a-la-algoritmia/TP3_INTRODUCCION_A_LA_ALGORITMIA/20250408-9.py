dia=int(input("Ingrese el dia a evaluar\n"))
mes= int(input("Ingrese el mes a evaluar\n"))
anio=int(input("Ingrese el año a evaluar\n"))

if dia<1 or dia>31 or mes<1 or mes>12 or anio<0:
    print("Fecha inválida")
elif (mes==4 or mes==6 or mes==9 or mes==11) and dia>30:
    print("Fecha inválida")
elif ((anio%4==0 and anio%100!=0)  or (anio%400==0)) and dia>29 and mes==2:
    print("Fecha inválida")
elif mes==2 and dia>28 and (not((anio%4==0 and anio%100!=0)  and (anio%400==0))):
    print("Fecha inválida")
else:
    print("Fecha válida")
