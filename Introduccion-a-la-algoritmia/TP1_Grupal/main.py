import random

usuarios=[[1,"Nicolas", "uade2025", "admin","Roosevelt2750"],[2,"Luana","uba2025","cliente","Tucuman2025"],[3,"Fran","itba2025","cliente","GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección
idProductos=[1,2,3,4,5,6,7,8,] # ID de cada elemento del menú
estadosPreparacion = ["En camino", "En preparación", "Entregado"] # Estados de los pedidos
idEstadosPreparacion = [1, 2, 3] # ID de los estados de preparación
productos = ["Pizza Italiana","Pizza Argentina","Pizza Cuatro Quesos","Pizza Muzzarella"]
preciosProductos = [1600, 1400, 1200, 1000]
id_productos = [1, 2, 3, 4]
idClientePedido= [1, 2, 3, 4, 5] # ID de cada cliente que hizo un pedido
idPedidos = [1, 2, 3, 4, 5] # ID de cada pedido
numeroOrden = [1, 2, 3, 4, 5] # Número de orden de cada pedido
idOrden = [1, 2, 3, 4, 5] # ID de cada pedido
idProductosOrden = [1, 2, 3, 4, 5] # ID de cada orden
idClienteOrden= [1, 2, 3, 2, 1] # ID de cada cliente que hizo un pedido
cantProductoOrden = [1, 2, 1, 3, 2] # Cantidad de cada pizza en pedidos
idEstadosPreparacionOrden=[1,3,2,1,3] # Estado de cada orden por ID
precioPedido = [1600, 1400, 1200, 1000, 1800] # Precio de cada pedido

iniciado=True
logueado=False

def sugerirProducto():
    sugerirProducto = True
    salir=False
    id_Producto=""
    sugerencia = random.randint(0, len(productos)-1)
    print("Te sugiero pedir:", productos[sugerencia])
    respuesta = input("¿Quieres pedir este producto? (si/no). si quieres salir escribe 'salir': ")

    while sugerirProducto:
        while respuesta != "si" and respuesta != "no" and respuesta != "salir":
            print("Respuesta no válida, por favor ingresa 'si', 'no' o 'salir'.")
            respuesta = input("¿Quieres pedir este producto? (si/no). si quieres salir escribe 'salir': ")

        if respuesta == "salir":
            sugerirProducto = False
            
        if respuesta == "si":
            print("Excelente elección, el producto será añadido a tu pedido.")
            id_Producto = sugerencia
            sugerirProducto = False
        elif respuesta == "no":
            while respuesta=="no":
                print("Entendido, te daré otra sugerencia.")
                sugerencia = random.randint(0, len(productos)-1)
                print("Te sugiero pedir:", productos[sugerencia])
                respuesta = input("¿Quieres pedir este producto? (si/no). si quieres salir escribe 'salir': ")


    return id_Producto

def login(nombreUsuario, contrasena):
    usuarioEncontrado = False
    usuario_loggeado = ""  
    numUsuario = 0
    while usuarioEncontrado == False and numUsuario < len(usuarios):
        if usuarios[numUsuario][1] == nombreUsuario and usuarios[numUsuario][2] == contrasena:
            usuario_loggeado = usuarios[numUsuario]
            usuarioEncontrado = True
        numUsuario += 1
    return usuario_loggeado  # If no match found, return blank

def agregarUsuario(nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion):
    ultimoUsuario=len(usuarios)
    idUsuario = ultimoUsuario+1
    usuarios.append([idUsuario, nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion])
    print("Usuario",nuevoUsuario," agregado con éxito.")
 
def eliminarUsuario(idUsuario):
    eliminado= False
    indice=0
    
    while eliminado==False:
        while indice < len(usuarios) and eliminado==False:
            if usuarios[indice][0] == idUsuario:  # Checking the user ID at index 0
                nombreUsuarioEliminar = usuarios[indice][1]  # Get the username for confirmation
                usuarios.pop(indice)  # Remove the user at the found index
                print("Usuario", nombreUsuarioEliminar, "eliminado con éxito.")
                eliminado = True
            else:
                indice = indice + 1

def modificarUsuario(idUsuario):
    modificado= False
    indice=0
    print("Que quieres modificar del usuario?")
    opcion = int(input("1. Nombre, 2. Contraseña, 3. Rol, 4. Dirección (ingrese el número de la opción): "))
    while opcion != 1 and opcion != 2 and opcion != 3 and opcion != 4:
        print("Opción no válida, por favor elige 1, 2, 3 o 4.")
        opcion = int(input("1. Nombre, 2. Contraseña, 3. Rol, 4. Dirección (ingrese el número de la opción): "))

    while indice < len(usuarios) and modificado==False:
        if usuarios[indice][0] == idUsuario:  # Checking the user ID at index 0
            if opcion == 1:
                nuevoNombre = input("Ingrese el nuevo nombre: ")
                usuarios[indice][1] = nuevoNombre
                print("Nombre modificado con éxito.")
                modificado = True
            elif opcion == 2:
                nuevaContrasena = input("Ingrese la nueva contraseña: ")
                usuarios[indice][2] = nuevaContrasena
                print("Contraseña modificada con éxito.")
                modificado = True
            elif opcion == 3:
                nuevoRol = input("Ingrese el nuevo rol (admin/cliente): ")
                while nuevoRol != "admin" and nuevoRol != "cliente":
                    print("Rol no válido, por favor ingrese 'admin' o 'cliente'.")
                    nuevoRol = input("Ingrese el nuevo rol (admin/cliente): ")
                usuarios[indice][3] = nuevoRol
                print("Rol modificado con éxito.")
                modificado = True
            elif opcion == 4:
                nuevaDireccion = input("Ingrese la nueva dirección: ")
                usuarios[indice][4] = nuevaDireccion
        else:
            indice = indice + 1
        
def mostrarUsuarios():
    for i in range(len(usuarios)):
        print(usuarios[i])

def guardarPedido(idUsuario, cantidad, idProducto):
    hacerPedido= True
    nuevonumeroOrden = len(numeroOrden) + 1  # Increment the order number

    while hacerPedido:
        nuevoidOrden = len(idOrden) + 1  # Increment the order ID
        estadoPedido = 1  # Default state is "En preparacion"
        precio = preciosProductos[idProducto - 1] * cantidad  # Calculate total price for the order
        
        idOrden.append(nuevoidOrden)
        idProductosOrden.append(idProducto)  # Append the product ID to the order list
        idClienteOrden.append(idUsuario)
        cantProductoOrden.append(cantidad)
        idEstadosPreparacionOrden.append(estadoPedido)
        numeroOrden.append(nuevonumeroOrden)
        precioPedido.append(precio)  # Append the price to the order list
        print("Pedido realizado con éxito. Número de orden:", nuevonumeroOrden, "ID de orden:", nuevoidOrden, "Producto:", productos[idProducto - 1], "Cantidad:", cantidad, "Precio total:", precio)
        otroPedido = input("¿Quieres hacer otro pedido? (si/no) ")

        while otroPedido != "si" and otroPedido != "no":
            print("Respuesta no válida, por favor ingresa 'si' o 'no'.")
            otroPedido = input("¿Quieres hacer otro pedido? (si/no) ")

        if otroPedido == "no":
            hacerPedido = False
        elif otroPedido == "si":
            opcionPedido = int(input("Sabes que pedir, o te doy una sugerencia? 1. Sé lo que quiero, 2. Dame una sugerencia. "))
            while opcionPedido < 1 or opcionPedido > 2:
                print("Opción no válida, por favor elige 1 o 2.")
                opcionPedido = int(input("Sabes que pedir, o te doy una sugerencia? 1. Sé lo que quiero, 2. Dame una sugerencia. "))
            crearPedido(opcionPedido)
            
def crearPedido(opcionPedido):
    productoElegido=False
    while productoElegido == False:
        if opcionPedido == 2:
            id_Producto = sugerirProducto()
            if id_Producto != "":
                print("Producto sugerido:", productos[id_Producto])
                cantidad = int(input("Ingrese la cantidad de este producto que desea: "))
                while cantidad < 1:
                    print("Cantidad no válida, debe ser mayor a 0.")
                    cantidad = int(input("Ingrese la cantidad de este producto que desea: "))
                guardarPedido(usuario[0], id_Producto, cantidad)
                productoElegido = True
            else:
                print("No se seleccionó ningún producto, volviendo al menú principal.")
                productoElegido = True
            

        elif opcionPedido == 1:
            id_Producto = int(input("Dime el numero de producto que quieres pedir."))
            while id_Producto < 1 or id_Producto > len(productos):
                print("ID de producto no válido, debe estar entre 1 y", len(productos))
                id_Producto = int(input("Dime el numero de producto que quieres pedir."))
            cantidad = int(input("Ingrese la cantidad de este producto que desea: "))
            while cantidad < 1:
                print("Cantidad no válida, debe ser mayor a 0.")
                cantidad = int(input("Ingrese la cantidad de este producto que desea: "))
            guardarPedido(usuario[0], id_Producto, cantidad)
            
        else:
            print("Opción inválida, por favor elige 1 o 2.")
            
def cancelarPedido(idPedido):
    if idPedido < 1 or idPedido > len(numeroOrden):
        print("ID de pedido inválido.")
    else:
        posicionPedido = idPedido - 1  # Adjust for zero-based index
        numeroOrden.pop(posicionPedido)  
        idOrden.pop(posicionPedido)
        idProductosOrden.pop(posicionPedido)
        idClienteOrden.pop(posicionPedido)
        cantProductoOrden.pop(posicionPedido)
        idEstadosPreparacionOrden.pop(posicionPedido)
        precioPedido.pop(posicionPedido)
        print("Pedido cancelado con éxito.")

def mostrarPedidos():
    for i in range(len(numeroOrden)-1):
        
        print("Numero de orden:", idOrden[i], "Producto:", productos[idProductosOrden[i] - 1], "Cliente:", usuarios[idClienteOrden[i] - 1][1], "Cantidad:", cantProductoOrden[i], "Estado:", estadosPreparacion[idEstadosPreparacionOrden[i] - 1], "Precio:", precioPedido[i])

def estadosDePreparacion():
    for i in range(len(idEstadosPreparacion)):
        print(idEstadosPreparacion[i], estadosPreparacion[i])        

def misPedidos():
    for i in range(len(idPedidos)):
        if idClienteOrden[i] == usuario[0]:
            print("Producto:", productos[idProductosOrden[i]], "Cantidad:", cantProductoOrden[i], "Estado:", estadosPreparacion[idEstadosPreparacionOrden[i] - 1], "Precio:", precioPedido[i])

def agregarProducto(nombreProducto, precioProducto):
    productos.append(nombreProducto)
    preciosProductos.append(precioProducto)
    nuevo_id = len(id_productos) + 1
    id_productos.append(nuevo_id)

def eliminarProducto(idProducto):
    if idProducto < 1 or idProducto > len(productos):
        print("ID de producto inválido.")
    else:
        productos.pop(idProducto - 1)  # Adjust for zero-based index
        preciosProductos.pop(idProducto - 1)
        id_productos.pop(idProducto - 1)
        print("Producto eliminado con éxito.")

def modificarProducto(idProducto, nuevoNombre, nuevoPrecio):
    if idProducto < 1 or idProducto > len(productos):
        print("ID de producto inválido.")
    else:
        productos[idProducto - 1] = nuevoNombre  # Adjust for zero-based index
        preciosProductos[idProducto - 1] = nuevoPrecio
        print("Producto modificado con éxito.")

def listarProductos():
    for i in range(len(productos)):
        print(id_productos[i], "Producto:", productos[i], "Precio:", preciosProductos[i])

print("Bienvenido a la pizzeria")
while iniciado==True:#iniciado==false equivale a pedir !iniciado
    while logueado==False:
        nombreUsuario = input("Ingrese su nombre de usuario: ")
        contrasena = input("Ingrese su contraseña: ")
        usuario = login(nombreUsuario, contrasena)
        if usuario != "":
            logueado = True
            print("Bienvenido", nombreUsuario)
        else:
            print("Usuario o contraseña incorrectos, intente de nuevo.")

    if usuario[3] == "admin":

        print("0.Salir, 1.Agregar usuario, 2. Eliminar un usuario, 3. Listar usuarios, 4. Ver pedidos, 5. Modificar usuario, 6. Agregar productos, 7. Listar productos, 8. Modificar productos, 9. Eliminar productos, 0. Salir")
        opcion = int(input("Seleccione una opción: "))
        while opcion < 0 or opcion > 9 or opcion == '':
            print("Opción no válida, por favor elige una opción entre 0 y 9.")
            opcion = int(input("Seleccione una opción: "))

        if opcion == 1:
            nuevoUsuario = input("Ingrese el nombre del nuevo usuario: ")
            nuevaContrasena = input("Ingrese la contraseña del nuevo usuario: ")
            nuevoUsuarioRol= input("Ingrese el rol del nuevo usuario (admin/cliente): ")
            while nuevoUsuarioRol != "admin" and nuevoUsuarioRol != "cliente":
                print("Rol no válido, por favor ingrese 'admin' o 'cliente'.")
                nuevoUsuarioRol = input("Ingrese el rol del nuevo usuario (admin/cliente): ")
            nuevoUsuarioDireccion = input("Ingrese la dirección del nuevo usuario: ")
            agregarUsuario(nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion)

        elif opcion == 2:
            mostrarUsuarios()
            idUsuario = int(input("Ingrese el id del usuario a eliminar: "))
            while idUsuario < 1 or idUsuario > len(usuarios):
                print("ID de usuario no válido, debe estar entre 1 y", len(usuarios))
                idUsuario = int(input("Ingrese el id del usuario a eliminar: "))
            eliminarUsuario(idUsuario)

        elif opcion == 3:
            mostrarUsuarios()

        elif opcion == 4:
            mostrarPedidos()

        elif opcion == 5:
            mostrarUsuarios()
            idUsuario = int(input("Ingrese el ID del usuario a modificar: "))
            while idUsuario < 1 or idUsuario > len(usuarios):
                print("ID de usuario no válido, debe estar entre 1 y", len(usuarios))
                idUsuario = int(input("Ingrese el ID del usuario a modificar: "))
            modificarUsuario(idUsuario)


        elif opcion == 6:
            nombreProducto = input("Ingrese el nombre del producto: ")
            precioProducto = float(input("Ingrese el precio del producto: "))
            agregarProducto(nombreProducto, precioProducto)

        elif opcion == 7:
            listarProductos()

        elif opcion == 8:
            listarProductos()
            idProducto = int(input("Ingrese el ID del producto a modificar: "))
            nuevoNombre = input("Ingrese el nuevo nombre del producto: ")
            nuevoPrecio = float(input("Ingrese el nuevo precio del producto: "))
            modificarProducto(idProducto, nuevoNombre, nuevoPrecio)

        elif opcion == 9:
            idProducto = int(input("Ingrese el ID del producto a eliminar: "))
            eliminarProducto(idProducto)

        elif opcion == 0:
            print("Saliendo del programa.")
            iniciado=False 

        else:
            print("Opción no válida")

    elif usuario[3] == "cliente":
        while iniciado==True:
            opcion = int(input("Selecciona una opción. 1. Carta, 2. Hacer un pedido, 3. Ver tus pedidos, 0. Salir. "))
            
            while opcion < 0 or opcion > 3:
                print("Opción no válida, por favor elige una opción entre 0 y 3.")
                opcion = int(input("Selecciona una opción. 1. Carta, 2. Hacer un pedido, 3. Ver tus pedidos, 0. Salir. "))

            while opcion>=1 and opcion <= 3 or opcion == 0:
                if opcion == 1:
                    print("Aquí está el menú.")
                    listarProductos()
                    opcion=-1
                elif opcion == 2:
                    listarProductos()
                    opcionPedido = int(input("Sabes que pedir, o te doy una sugerencia? 1. Sé lo que quiero, 2. Dame una sugerencia. "))
                    while opcionPedido < 1 or opcionPedido > 2:
                        print("Opción no válida, por favor elige 1 o 2.")
                        opcionPedido = int(input("Sabes que pedir, o te doy una sugerencia? 1. Sé lo que quiero, 2. Dame una sugerencia. "))
                    crearPedido(opcionPedido)
                elif opcion == 3:
                    misPedidos()
                # elif opcion == -1:
                #     print("Volviendo al menú principal.")
                elif opcion == 0:
                    print("Saliendo del programa.")
                    iniciado=False
                    opcion=-1
    