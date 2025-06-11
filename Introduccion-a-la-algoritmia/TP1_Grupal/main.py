usuarios=[[1,"Nicolas", "uade2025", "admin","Roosevelt2750"],[2,"Luana","uba2025","cliente","Tucuman2025"],[3,"Fran","itba2025","cliente","GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección

idProductos=[1,2,3,4,5,6,7,8,] # ID de cada elemento del menú
preciosProductos = [1600, 1400, 1200, 1000, 1800, 2000, 2200, 2400] # Precios de cada producto
productos = ["Pizza Italiana","Pizza Argentina","Pizza Cuatro Quesos","Pizza Muzzarella"]

estadosPreparacion = ["En camino", "En preparación", "Entregado"] # Estados de los pedidos
idEstadosPreparacion = [1, 2, 3] # ID de cada estado de pedido

numeroOrden = [1, 2, 3, 4, 5] # Número de orden de cada pedido
idOrden = [1, 2, 3, 4, 5] # ID de cada pedido
idProductosOrden = [1, 2, 3, 4, 5] # ID de cada orden
idClienteOrden= [1, 2, 3, 4, 5] # ID de cada cliente que hizo un pedido
cantProductoOrden = [1, 2, 1, 3, 2] # Cantidad de cada pizza en pedidos
idEstadosPreparacionOrden=[1,3,2,1,3] # Estado de cada orden por ID
precioPedido = [1600, 1400, 1200, 1000, 1800] # Precio de cada pedido

iniciado=False
logueado=False

def login(nombreUsuario, contrasena):
    usuarioEncontrado = False
    usuario = ["", "", "", "", ""]  # Default blank user
    numUsuario = 0
    while usuarioEncontrado == False:
        if usuarios[numUsuario][1] == nombreUsuario and usuarios[numUsuario][2] == contrasena:
            usuario= usuarios[numUsuario]
            usuarioEncontrado = True
        numUsuario += 1
    return usuario  # If no match found, return blank

def agregarUsuario(nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion):
    ultimoUsuario=len(usuarios)-1
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
    opcion = input("1. Nombre, 2. Contraseña, 3. Rol, 4. Dirección")
    while modificado==False:
        while indice < len(usuarios) and modificado==False:
            if usuarios[indice][0] == idUsuario:  # Checking the user ID at index 0
               if opcion == "1":
                   nuevoNombre = input("Ingrese el nuevo nombre: ")
                   usuarios[indice][1] = nuevoNombre
               elif opcion == "2":
                   nuevaContrasena = input("Ingrese la nueva contraseña: ")
                   usuarios[indice][2] = nuevaContrasena
               elif opcion == "3":
                   nuevoRol = input("Ingrese el nuevo rol (admin/cliente): ")
                   usuarios[indice][3] = nuevoRol
               elif opcion == "4":
                   nuevaDireccion = input("Ingrese la nueva dirección: ")
                   usuarios[indice][4] = nuevaDireccion
               print("Usuario modificado con éxito.")
               modificado = True
            else:
                indice = indice + 1

def mostrarUsuarios():
    for i in range(len(usuarios)):
        print(usuarios[i])

def nuevoPedido(idUsuario, cantidad, idProducto):
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
        otroPedido = input("¿Quieres hacer otro pedido? (si/no) ")
        if otroPedido != "si":
            hacerPedido = False

def modificarPedido(idPedido, nuevoEstado):
    if idPedido < 1 or idPedido > len(numeroOrden):
        print("ID de pedido inválido.")
    else:
        print("Aca hay q modificar el pedido")

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
    for i in range(len(numeroOrden)):
        print(i, numeroOrden[i])

def estadosDePreparacion():
    for i in range(len(idEstadosPreparacion)):
        print(idEstadosPreparacion[i], estadosPreparacion[i])        

def misPedidos():
    for i in range(len(numeroOrden)):
        if idClienteOrden[i] == usuario[0]:
            print("Producto:", productos[i], "Cantidad:", cantProductoOrden[i], "Estado:", estadosPreparacion[idEstadosPreparacionOrden[i] - 1], "Precio:", precioPedido[i])

def agregarProducto(nombreProducto, precioProducto):
    productos.append(nombreProducto)
    preciosProductos.append(precioProducto)
    nuevoId = len(idProductos) + 1
    idProductos.append(nuevoId)

def eliminarProducto(idProducto):
    if idProducto < 1 or idProducto > len(productos):
        print("ID de producto inválido.")
    else:
        posicionProducto = idProducto - 1  # Adjust for zero-based index
        productos.pop(posicionProducto)  
        preciosProductos.pop(posicionProducto)
        idProductos.pop(posicionProducto)
        print("Producto eliminado con éxito.")

def modificarProducto(idProducto, nuevoNombre, nuevoPrecio):
    if idProducto < 1 or idProducto > len(productos):
        print("ID de producto inválido.")
    else:
        posicionProducto = idProducto - 1  # Adjust for zero-based index
        productos[posicionProducto] = nuevoNombre
        preciosProductos[posicionProducto] = nuevoPrecio
        print("Producto modificado con éxito.")

def listarProductos():
    for i in range(len(productos)):
        print(idProductos[i], "Producto:", productos[i], "Precio:", preciosProductos[i])

def modificarEstadoPreparacionPedido(idPedido, nuevoEstado):
    if idPedido < 1 or idPedido > len(numeroOrden):
        print("ID de pedido inválido.")
    else:
        posicionPedido = idPedido - 1  # Adjust for zero-based index
        idEstadosPreparacionOrden[posicionPedido] = nuevoEstado
        print("Estado del pedido modificado con éxito.")

print("Bienvenido a la pizzeria")
while iniciado==False:
    while logueado==False:
        nombreUsuario = input("Ingrese su nombre de usuario: ")
        contrasena = input("Ingrese su contraseña: ")
        usuario = login(nombreUsuario, contrasena)
        if usuario != "":
            logueado = True
            print("Bienvenido", nombreUsuario)
        else:
            print("Usuario o contraseña incorrectos, intente de nuevo.")
    #print("Bienvenido", nombreUsuario)

    if usuario[3] == "admin":

        print("1.Agregar usuario, 2. Eliminar un usuario, 3. Listar usuarios, 4. Ver pedidos, 5. Modificar usuario, 6. Agregar productos, 7. Listar productos, 8. Modificar productos, 9. Eliminar productos, 10. Modificar estado pedido, 0. Salir")
        opcion = input("Seleccione una opción: ")
        
        if opcion == "1":
            nuevoUsuario = input("Ingrese el nombre del nuevo usuario: ")
            nuevaContrasena = input("Ingrese la contraseña del nuevo usuario: ")
            nuevoUsuarioRol= input("Ingrese el rol del nuevo usuario (admin/cliente): ")
            nuevoUsuarioDireccion = input("Ingrese la dirección del nuevo usuario: ")
            agregarUsuario(nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion)

        elif opcion == "2":
            mostrarUsuarios()
            idUsuario = int(input("Ingrese el id del usuario a eliminar: "))
            eliminarUsuario(idUsuario)

        elif opcion == "3":
            mostrarUsuarios()

        elif opcion == "4":
            mostrarPedidos()
                 
        elif opcion == "5":
            print("Saliendo del programa.")
            iniciado=False
        
        elif opcion == "6":
            nombreProducto = input("Ingrese el nombre del producto: ")
            precioProducto = float(input("Ingrese el precio del producto: "))
            agregarProducto(nombreProducto, precioProducto)

        elif opcion == "7":
            listarProductos()
        
        elif opcion == "8":
            listarProductos()
            idProducto = int(input("Ingrese el ID del producto a modificar: "))
            nuevoNombre = input("Ingrese el nuevo nombre del producto: ")
            nuevoPrecio = float(input("Ingrese el nuevo precio del producto: "))
            modificarProducto(idProducto, nuevoNombre, nuevoPrecio)
        
        elif opcion == "9":
            idProducto = int(input("Ingrese el ID del producto a eliminar: "))
            eliminarProducto(idProducto)
        
        elif opcion == "10":
            
            idPedido = int(input("Ingrese el ID del pedido a modificar: "))
            print("Estados disponibles:")
            estadosDePreparacion()
            nuevoEstado = int(input("Ingrese el nuevo estado del pedido (1-3): "))
            modificarEstadoPreparacionPedido(idPedido, nuevoEstado)
        
        else:
            print("Opción no válida")

    elif usuario[3] == "cliente":
        opcion = input("Selecciona una opción. 1. Menu, 2. Hacer un pedido, 3. Modificar un pedido, 4. Cancelar un pedido, 5. Ver mis pedidos, 0. Salir: ")
        
        if opcion == "1":
            print("Aquí está el menú.")
            listarProductos()
        
        elif opcion == "2":
            listarProductos()
            print("Pedido:")
            idProducto = int(input("Ingresa el ID del producto que deseas pedir: "))
            cantidad = int(input("¿Cuántas pizzas deseas pedir? "))
            nuevoPedido(usuario[0], cantidad, idProducto)
            print("Orden realizada con éxito.")


        elif opcion == "3":
            misPedidos()
            opcionModificar = input("¿Qué deseas modificar? 1. Cantidad del pedido, ")
        
        elif opcion=="4":
            misPedidos()
            idPedido = int(input("Ingrese el ID del pedido que desea cancelar: "))
            cancelarPedido(idPedido)
        
        elif opcion == "5":
            misPedidos()
        
        else:
            print("Opción no válida")
