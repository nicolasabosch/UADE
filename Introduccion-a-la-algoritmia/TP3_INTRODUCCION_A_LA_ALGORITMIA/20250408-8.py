anio=int(input("Dime un año: "))

if anio%4==0 and anio%100==0 and anio%400==0:
    print("Es bisiesto")

elif anio%4==0 and anio%100==0:
    print("No es bisiesto")

