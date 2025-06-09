usuarios=[[1,"Nicolas", "uade2025", "admin","Roosevelt2750"],[2,"Luana","uba2025","cliente","Tucuman2025"],[3,"Fran","itba2025","cliente","GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección
idProductos=[1,2,3,4,5,6,7,8,] # ID de cada elemento del menú
nombreProductos=["Muzarella","Napolitana","Calabresa","Fugazzeta","Vegetariana","4 Quesos","Peperoni","Barbacoa"]
preciosProductos=[1000,1200,1300,1400,1500,1600,1700,1800]
pedidos=[[0,0,3],[0,2,1],[1,4,3]] # idUsuario, idProductos, Cantidad
iniciado=False
logueado=False

def login(nombreUsuario, contrasena):
    usuario = ["", "", "", "", ""]  # Default blank user
    for i in range(len(usuarios)): #Login
        if usuarios[i][1] == nombreUsuario and usuarios[i][2] == contrasena:
            usuario= usuarios[i]
    
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

def mostrarUsuarios():
    for i in range(len(usuarios)):
        print(usuarios[i])

def nuevoPedido(idUsuario, idProducto, cantidad):

    pedidos.append([idUsuario, idProducto, cantidad])
    print("Pedido agregado con éxito.")

def mostrarPedidos():
    for i in range(len(pedidos)):
        print(i, pedidos[i])

def verMenu():
    for i in range(len(nombreProductos)):
                print(nombreProductos[i])

def misPedidos():
    for i in range(len(pedidos)):
        if pedidos[i][0] == usuario[0]:
            print("Producto:",pedidos[i][1],"Cantidad:",pedidos[i][2])

def nombreProductoToIdProducto(nombreProducto):
    for i in range(len(nombreProductos)):
            if nombreProductos[i] == nombreProducto:
                idProducto = idProductos[i]
                return idProducto

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

        print("1.Agregar usuario, 2. Eliminar un usuario, 3. Listar usuarios, 4. Ver pedidos, 5. Modificar usuario, 6. Salir")
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
        
        else:
            print("Opción no válida")

    elif usuario[3] == "cliente":
        print("Bienvenido cliente, selecciona una opcion. 1. Menu, 2. Hacer un pedido, 3. Ver tus pedidos")
        opcion = input("Seleccione una opción: ")
        if opcion == "1":
            print("Aquí está el menú.")
            verMenu()
        
        elif opcion == "2":
            verMenu()
            print("Pedido:")
            nombreProducto = input("Ingrese el nombre del producto: ")
            idProducto = nombreProductoToIdProducto(nombreProducto)

            cantidad = int(input("Ingrese la cantidad: "))
            nuevoPedido(usuario[0], idProducto, cantidad)

        elif opcion == "3":
            misPedidos()
        
        else:
            print("Opción no válida")

    else:
        print("No existe el tipo de usuario ingresado, intente de vuelta.")

