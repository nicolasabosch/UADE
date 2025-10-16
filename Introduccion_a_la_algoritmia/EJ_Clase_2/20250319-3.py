montoFactura= int(input("Ingrese el monto de la factura: "))
metodoDePago= input("¿pagas en efectivo o en 3 cuotas?")
montoContado= montoFactura*0.85
montoEnCuotas= montoFactura*1.20

if(metodoDePago=="efectivo"):

    print("tu factura pagando al contado es de $", montoContado)

else:
    print("tu factura pagando en cuotas es de $", montoEnCuotas, " y cada cuota es de ", montoEnCuotas/3)
