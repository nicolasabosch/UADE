usuarios=[[1,"Nicolas", "uade2025", "admin","Roosevelt2750"],[2,"Luana","uba2025","cliente","Tucuman2025"],[3,"Fran","itba2025","cliente","GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección
idProductos=[1,2,3,4,5,6,7,8,] # ID de cada elemento del menú
preciosProductos = [1600, 1400, 1200, 1000]
productos = ["Pizza Italiana","Pizza Argentina","Pizza Cuatro Quesos","Pizza Muzzarella"]

idEstados = [1, 2, 3] # ID de cada estado de pedido
estados = ["En camino", "En preparación", "Entregado"] # Estados de los pedidos

numeroOrden = [1, 2, 3, 4, 5] # Número de orden de cada pedido
idOrden = [1, 2, 3, 4, 5] # ID de cada pedido
idProductosOrden = [1, 2, 3, 4, 5] # ID de cada orden
idClienteOrden= [1, 2, 3, 4, 5] # ID de cada cliente que hizo un pedido
cantProductoOrden = [1, 2, 1, 3, 2] # Cantidad de cada pizza en pedidos
estadosOrdenesID=[1,3,2,1,3] # Estado de cada orden por ID

respuesta = input("Queres pedir una pizza? (si/no)")

if respuesta == "si":
    nuevonumeroOrden = len(numeroOrden) + 1  # Increment the order number

    nuevoidOrden = len(idOrden) + 1  # Increment the order ID
    nuevoidClienteOrden = int(input("Ingresa tu ID de cliente: "))
    nuevoidProductoOrden = int(input("Ingresa el ID del producto que deseas pedir: "))
    cantidad = int(input("¿Cuántas pizzas deseas pedir? "))
    estadoPedido = 1  # Default state is "En preparacion"

    idOrden.append(nuevoidOrden)
    idProductosOrden.append(nuevoidProductoOrden)
    idClienteOrden.append(nuevoidClienteOrden)
    cantProductoOrden.append(cantidad)
    estadosOrdenesID.append(estadoPedido)
    numeroOrden.append(nuevonumeroOrden)

    otroPedido = input("¿Quieres hacer otro pedido? (si/no) ")
    while otroPedido.lower() == "si":
        nuevoidOrden = len(idOrden) + 1  # Increment the order ID
        nuevoidClienteOrden = int(input("Ingresa tu ID de cliente: "))
        nuevoidProductoOrden = int(input("Ingresa el ID del producto que deseas pedir: "))
        cantidad = int(input("¿Cuántas pizzas deseas pedir? "))
        estadoPedido = 1  # Default state is "En preparacion"

        idOrden.append(nuevoidOrden)
        idProductosOrden.append(nuevoidProductoOrden)
        idClienteOrden.append(nuevoidClienteOrden)
        cantProductoOrden.append(cantidad)
        estadosOrdenesID.append(estadoPedido)
        numeroOrden.append(nuevonumeroOrden)  # Increment the order number

        otroPedido = input("¿Quieres hacer otro pedido? (si/no) ")




