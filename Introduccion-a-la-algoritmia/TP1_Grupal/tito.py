def sugerirProducto(opcionPedido):
    if opcionPedido == "2":
        while opcionPedido != "1":
            sugerencia = random.randint(0, len(productos)-1)
            print("Te sugiero pedir:", productos[sugerencia])
            respuesta = input("¿Quieres pedir este producto? (si/no): ")
            while respuesta!= "si" and respuesta != "no":
                respuesta = input("Respuesta no válida. Por favor, responde con 'si' o 'no': ")
            if respuesta == "si":
                print("Excelente elección, el producto será añadido a tu pedido.")
                id_Producto = sugerencia
                opcionPedido = "1"
            elif respuesta == "no":
                print("Entendido, te daré otra sugerencia.")
            

    return id_Producto



elif opcion == 2:
                listarProductos()
                opcionPedido = int(input("Sabes que pedir, o te doy una sugerencia? 1. Sé lo que quiero, 2. Dame una sugerencia. "))
                if opcionPedido == 2:
                    id_Producto = sugerirProducto(opcionPedido)
                    cantidad = int(input("Ingrese la cantidad de este producto que desea: "))
                    
                elif opcionPedido == 1:
                    id_Producto = int(input("Dime el numero de producto que quieres pedir."))
                    if(id_Producto>1 and id_Producto<=len(productos)):
                        cantidad = int(input("Ingrese la cantidad: "))
                        nuevoPedido(usuario[0], id_Producto, cantidad)
                else:
                    print("Opción inválida, por favor elige 1 o 2.")

                