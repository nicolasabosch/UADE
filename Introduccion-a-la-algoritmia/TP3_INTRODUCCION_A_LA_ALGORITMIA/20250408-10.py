sueldoBasico= float(input("Ingrese su sueldo basico\n"))
antiguedad= int(input("Ingrese su antiguedad\n"))
estadoCivil=int(input("Ingrese 1 si es soltero, 2 si es casado\n"))
sueldoNeto=0

if estadoCivil==1:
    sueldoNeto=(sueldoBasico*0.05)*antiguedad
    estadoCivil="Soltero"
elif estadoCivil==2:
    sueldoNeto=(sueldoBasico*0.07)*antiguedad
    estadoCivil="Casado"

jubilacion= sueldoBasico*0.11
obraSocial= sueldoBasico*0.03
sindicato= sueldoBasico*0.03

sueldoNeto=sueldoBasico-jubilacion-obraSocial-sindicato+sueldoNeto

print("Estado Civil: ", estadoCivil,"\n")
print("Sueldo Basico: ", sueldoBasico,"\n")
print("Antiguedad: ", antiguedad,"\n")
print("Jubilación: ", jubilacion,"\n")
print("Obra Social: ", obraSocial,"\n")
print("Sindicato: ", sindicato,"\n")
print("El sueldo neto resultante es: ", sueldoNeto,"\n")