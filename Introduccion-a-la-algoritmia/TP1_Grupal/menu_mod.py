productos = ["Pizza Italiana","Pizza Argentina","Pizza Cuatro Quesos","Pizza Muzzarella"]
precios = [1600, 1400, 1200, 1000]
id_productos = [0, 1, 2, 3]
#si quiero agregar un id:
#nuevo_id = ultimo_id + 1
#id_productos.append(nuevo_id) #lista.append(elem) agrega al final de lista el elemento elem

opciones_admin =["1. Agregar producto  ","2. Eliminar producto"]
print(opciones_admin)
opciones_admin = int(input("introduzca la opción deseada: "))

if opciones_admin == 1:
    #agrego a la lista de productos
    agregar_producto = input("ingrese el nombre del producto: ")
    productos.append(agregar_producto)

    #agrego el precio del prod a la lista de precios
    nuevo_precio = int(input("ingrese el precio del producto"))
    precios.append(nuevo_precio)

    #agrego el id a la lista de ids
    nuevo_id = len(id_productos)
    id_productos.append(nuevo_id)

    print("Nueva lista de productos y precios:", productos,precios)

elif opciones_admin == 2:
    print("eliminar producto", productos, id_productos)

    id_producto_a_eliminar = int(input("ingrese el id del producto a eliminar: "))
    if(id_producto_a_eliminar<0 or id_producto_a_eliminar >= len(productos)):# si no se corresponde con un id
        print("id inválido")
    else:
        productos.pop(id_producto_a_eliminar)#elimino el producto q se corresponde con el id que me pasaron

        #elimino el ultimo id asi quedan todos consecutivos
        id_productos.pop(len(productos))#elimino el ultimo id(que se corresponde con la longitud de la lista de productos actualizada), talque ahora cambian los id.

        #elimino el precio que estaba en la posicion a eliminar
        precios.pop(id_producto_a_eliminar)

        print("Nueva lista de productos y precios: ", productos,precios)
else:
    print("opción no válida")

    elif eleccion_usuario == 2:
    print("Bienvenido cliente")
    print("Por favor, elija una opción: ")
    print(productos)
    print(id_productos)
    id_elegido = int(input("Ingrese el id de la opción deseada, para salir ingrese -1: "))
    if id_elegido!=-1:
        #chequeamos id valido
        if id_elegido >= len(productos):#id invalido
            print("id inválido")
        else:#el id es valido, puede hacer un peddido
            print(productos[id_elegido],precios[id_elegido])
            #aca se toma el pedido
    else:
        print("usted ha elegido salir")