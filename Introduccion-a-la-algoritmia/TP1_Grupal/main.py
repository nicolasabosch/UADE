usuarios=[[0,"Nicolas", "uade2025", "admin","Roosevelt2750"],[1,"Luana","uba2025","cliente","Tucuman2025"],[2,"Fran","itba2025","cliente","GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección
idProductos=[0,1,2,3,4,5,6,7] # ID de cada elemento del menú
nombreProductos=["Muzarella","Napolitana","Calabresa","Fugazzeta","Vegetariana","4 Quesos","Peperoni","Barbacoa"]
preciosProductos=[1000,1200,1300,1400,1500,1600,1700,1800]
pedidos=[[0,"Muzarella",2],[0,"Calabresa",1],[1,"Vegetariana",3]] # idUsuario, producto, Cantidad
iniciado=False
logueado=False

def login(nombreUsuario, contrasena):
    
    for i in range(len(usuarios)): #Login
        if usuarios[i][1] == nombreUsuario and usuarios[i][2] == contrasena:
            return usuarios[i]
    
    return None  # If no match found, return False
        
def agregarUsuario(nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion):
    
    ultimoUsuario=usuarios[len(usuarios)[0]]
    idUsuario = ultimoUsuario + 1
    usuarios.append([idUsuario, nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion])
    print("Usuario",nuevoUsuario," agregado con éxito.")
 
def eliminarUsuario(idUsuario):
    eliminado= False
    indice=0
    mostrarUsuarios()
    while eliminado==False:
        while indice < len(usuarios) and eliminado==False:
            if usuarios[indice][0] == idUsuario:  # Checking the user ID at index 0
                nombreUsuario = usuarios[indice][1]  # Get the username for confirmation
                usuarios.pop(indice)  # Remove the user at the found index
                print("Usuario", nombreUsuario, "eliminado con éxito.")
                eliminado = True
            indice += 1

def mostrarUsuarios():
    for i in range(len(usuarios)):
        print(i, usuarios[i])


    for i in range(len(usuarios)):
        if usuarios[i][1] == nombreUsuario:
            return usuarios[i][3]  # Return the role of the user
    return None

def nuevoPedido(idUsuario, idProducto, cantidad):

    pedidos.append([idUsuario, idProducto, cantidad])
    print("Pedido agregado con éxito.")

def mostrarPedidos():
    for i in range(len(pedidos)):
        print(i, pedidos[i])

def verMenu():
    for i in range(len(nombreProductos)):
                print(i,nombreProductos[i])

def misPedidos():
    for i in range(len(pedidos)):
        if pedidos[i][0] == usuario[0]:
            print("Producto:",pedidos[i][1],"Cantidad:",pedidos[i][2])

def nombreProductoToIdProducto(nombreProducto):
    for i in range(len(nombreProductos)):
            if nombreProductos[i] == nombreProducto:
                idProducto = idProductos[i]
                return idProducto


print("Bienvenido a la pizzeria")
while iniciado==False:
    while logueado==False:
        nombreUsuario = input("Ingrese su nombre de usuario: ")
        contrasena = input("Ingrese su contraseña: ")
        usuario = login(nombreUsuario, contrasena)
        if usuario != None:
            logueado = True
        else:
            print("Usuario o contraseña incorrectos, intente de nuevo.")
    print("Bienvenido", nombreUsuario)
    

    if usuario[3] == "admin":

        print("1.Agregar usuario, 2. Eliminar un usuario, 3. Listar usuarios, 4. Ver pedidos, 5. Salir")
        
        opcion = input("Seleccione una opción: ")
        if opcion == "1":
            nuevoUsuario = input("Ingrese el nombre del nuevo usuario: ")
            nuevaContrasena = input("Ingrese la contraseña del nuevo usuario: ")
            nuevoUsuarioRol= input("Ingrese el rol del nuevo usuario (admin/cliente): ")
            nuevoUsuarioDireccion = input("Ingrese la dirección del nuevo usuario: ")
            agregarUsuario(nuevoUsuario, nuevaContrasena, nuevoUsuarioRol, nuevoUsuarioDireccion)

        elif opcion == "2":
            idUsuario = int(input("Ingrese el id del usuario a eliminar: "))
            eliminarUsuario(idUsuario)

        elif opcion == "3":
            mostrarUsuarios()

        elif opcion == "4":
            mostrarPedidos()
            
        
        elif opcion == "5":
            print("Volviendo al menú principal.")
        
        else:
            print("Opción no válida")

    elif usuario[3] == "cliente":
        print("Bienvenido cliente, selecciona una opcion. 1. Menu, 2. Hacer un pedido, 3. Ver tus pedidos")
        opcion = input("Seleccione una opción: ")
        if opcion == "1":
            print("Aquí está el menú.")
            verMenu()
        elif opcion == "2":
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

