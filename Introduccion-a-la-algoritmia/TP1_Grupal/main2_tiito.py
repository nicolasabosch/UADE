usuarios=[[1,"Nicolas", "uade2025", "admin","Roosevelt2750"],[2,"Luana","uba2025","cliente","Tucuman2025"],[3,"Fran","itba2025","cliente","GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección
idProductos=[1,2,3,4,5,6,7,8,] # ID de cada elemento del menú
estadosPedidos = ["En camino", "En preparación", "Entregado"] # Estados de los pedidos

productos = ["Pizza Italiana","Pizza Argentina","Pizza Cuatro Quesos","Pizza Muzzarella"]
precios = [1600, 1400, 1200, 1000]
id_productos = [1, 2, 3, 4]
idClientePedido= [1, 2, 3, 4, 5] # ID de cada cliente que hizo un pedido
idPedidos = [1, 2, 3, 4, 5] # ID de cada pedido
numeroOrden = [1, 2, 3, 4, 5] # Número de orden de cada pedido

pizzaEnPedido = ["Pizza de champiñones", "Pizza de pepperoni", "Pizza de atún", "Pizza de jamón y queso", "Pizza vegetariana"] # Lista de pizzas en pedidos
cantidadEnPedido = [1, 2, 1, 3, 2] # Cantidad de cada pizza en pedidos


idOrdenes = [1, 2, 3, 4, 5] # ID de cada orden
pedidos=[]  # List to store orders, each order will be a list of [idUsuario, idProducto, cantidad]

iniciado=False
logueado=False

def login(nombreUsuario, contrasena):
    usuarioEncontrado = False
    usuario_loggeado = ["incorrecto", "", "", "", ""]  # Default blank user
    numUsuario = 0
    while usuarioEncontrado == False and numUsuario < len(usuarios):
        if usuarios[numUsuario][1] == nombreUsuario and usuarios[numUsuario][2] == contrasena:
            usuario_loggeado = usuarios[numUsuario]
            usuarioEncontrado = True
        numUsuario += 1
    return usuario_loggeado  # If no match found, return blank

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

def nuevoPedido(idUsuario, idProducto, cantidad):
    pedidos.append([idUsuario, idProducto, cantidad])
    print("Pedido agregado con éxito.")

def mostrarPedidos():
    for i in range(len(pedidos)):
        print(i, pedidos[i])

def misPedidos():
    for i in range(len(pedidos)):
        if pedidos[i][0] == usuario[0]:
            print("Producto:",pedidos[i][1],"Cantidad:",pedidos[i][2])

def agregarProducto(nombreProducto, precioProducto):
    productos.append(nombreProducto)
    precios.append(precioProducto)
    nuevo_id = len(id_productos) + 1
    id_productos.append(nuevo_id)

def eliminarProducto(idProducto):
    if idProducto < 1 or idProducto > len(productos):
        print("ID de producto inválido.")
    else:
        productos.pop(idProducto - 1)  # Adjust for zero-based index
        precios.pop(idProducto - 1)
        id_productos.pop(idProducto - 1)
        print("Producto eliminado con éxito.")

def modificarProducto(idProducto, nuevoNombre, nuevoPrecio):
    if idProducto < 1 or idProducto > len(productos):
        print("ID de producto inválido.")
    else:
        productos[idProducto - 1] = nuevoNombre  # Adjust for zero-based index
        precios[idProducto - 1] = nuevoPrecio
        print("Producto modificado con éxito.")

def listarProductos():
    for i in range(len(productos)):
        print(id_productos[i], "Producto:", productos[i], "Precio:", precios[i])

print("Bienvenido a la pizzeria")
while iniciado==False:#iniciado==false equivale a pedir !iniciado
    while logueado==False:
        nombreUsuario = input("Ingrese su nombre de usuario: ")
        contrasena = input("Ingrese su contraseña: ")
        usuario = login(nombreUsuario, contrasena)
        if usuario[0] != 'incorrecto':
            logueado = True
            print("Bienvenido", nombreUsuario)
        else:
            print("Usuario o contraseña incorrectos, intente de nuevo.")
    #print("Bienvenido", nombreUsuario)

    if usuario[3] == "admin":

        print("0.Salir, 1.Agregar usuario, 2. Eliminar un usuario, 3. Listar usuarios, 4. Ver pedidos, 5. Modificar usuario, 6. Agregar productos, 7. Listar productos, 8. Modificar productos, 9. Eliminar productos, 0. Salir")
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

        elif opcion == "0":
            print("Saliendo del programa.")
            iniciado=True 

        else:
            print("Opción no válida")

    elif usuario[3] == "cliente":
        opcion = input("Selecciona una opción. 1. Menu, 2. Hacer un pedido, 3. Ver tus pedidos, 0. Salir. ")
        if opcion == "1":
            print("Aquí está el menú.")
            listarProductos()
        
        elif opcion == "2":
            listarProductos()
            print("Pedido:")
            id_Producto = int(input("Ingrese el ID del producto: "))

            if(id_Producto>1 and id_Producto<=len(productos)):
                cantidad = int(input("Ingrese la cantidad: "))
                nuevoPedido(usuario[0], id_Producto, cantidad)
            else:
                print("ID de producto inválido. Regrese a las opciones disponibles.")

        elif opcion == "3":
            misPedidos()
        
        elif opcion == "0":
            print("Saliendo del programa.")
            iniciado=True        
        else:
            print("Opción inválida, regrese a las opciones disponibles.")

    #else:
    #    print("Usuario o Contraseña incorrecta, intente de vuelta.")
    #   usuario = input("Ingrese el nombre de usuario: ")
    #   contrasena = input("Ingrese la contraseña: ")
