# Una inmobiliaria paga a sus vendedores un salario de $250000, 
# mas una comisión de $50000 por cada venta realizada, mas el 5% del valor de las ventas.
# Realizar un programa que imprima el numero del vendedor y el salario que le corresponde en un determinado mes. 
# Se leen el numero del vendedor, la cantidad de ventas que realizo y el valor total de las mismas.

salario=250000
comisionVenta=50000
numeroVendedor = int(input("Ingrese el número del vendedor: "))
cantidadVentas = int(input("Ingrese la cantidad de ventas realizadas: "))
valorTotalVentas = int(input("Ingrese el valor total de las ventas: "))

comisionPorVentas = cantidadVentas * comisionVenta
comisionPorValor = valorTotalVentas * 0.05
salarioTotal = salario + comisionPorVentas + comisionPorValor

print("El vendedor número", numeroVendedor, "tiene un salario total de $", salarioTotal, "este mes.")