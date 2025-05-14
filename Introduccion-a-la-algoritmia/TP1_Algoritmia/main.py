usuarios=[["Nicolas", "uade2025", "admin"],["Luana","uba2025","cliente"],["Fran","itba2025","cliente"]]
tipoUsuario=["admin","cliente"]
menu=[]
pedidos=[]
iniciado=False

while iniciado==False:
    print("Bienvenido a la pizzeria")
    usuarioActual=input("Ingrese su tipo de usuario: ")
    if usuarioActual in tipoUsuario:
        if usuarioActual=="admin":
            
            print("Bienvenido admin, si quieres agregar un usuario ingresa 1, si quieres eliminar un usuario ingresa 2, si quieres ver los usuarios ingresa 3, si quieres ver los pedidos ingresa 4, si quieres volver ingresa 5")
            
            opcion = input("Seleccione una opción: ")
            if opcion == "1":
                nuevoUsuario = input("Ingrese el nombre del nuevo usuario: ")
                nuevaContrasena = input("Ingrese la contraseña del nuevo usuario: ")
                nuevousuarioRol= input("Ingrese el rol del nuevo usuario (admin/cliente): ").lower()
                usuarios.append([nuevoUsuario, nuevaContrasena, nuevousuarioRol])
                print("Usuario",nuevoUsuario," agregado con éxito.")
                
            elif opcion == "2":
                usuarioEliminar = input("Ingrese el nombre del usuario a eliminar: ")
                if usuarioEliminar in usuarios:
                    usuarios.pop(usuarios.index(usuarioEliminar)) #checkear xq no esta bien 
                else:
                    print("Usuario no encontrado.")
            elif opcion == "3":
                for i in range(len(usuarios)):
                    print(i,usuarios[i])
                
            elif opcion == "4":
                for i in range(len(pedidos)):
                    print(i,pedidos[i])
                print("Aquí puedes ver los pedidos.")
            
            elif opcion == "5":
                print("Volviendo al menú principal.")
            
            else:
                print("Opción no válida")
        elif usuarioActual=="cliente":
            print("Bienvenido cliente, si quieres ver el menu ingresa 1, si quieres hacer un pedido ingresa 2, si quieres ver tus pedidos ingresa 3")
            opcion = input("Seleccione una opción: ")
            if opcion == "1":
                print("Aquí está el menú.")
                for i in range(len(menu)):
                    print(i,menu[i])
            elif opcion == "2":
                nuevoPedido = input("Ingrese su pedido: ")
                pedidos.append(nuevoPedido)
                print("Pedido",nuevoPedido,"agregado con éxito.")
            elif opcion == "3":
                for i in range(len(pedidos)):
                    print(i,pedidos[i])
                print("Aquí puedes ver tus pedidos.")
            else:
                print("Opción no válida")   
    
        else:
            print("No existe el tipo de usuario ingresado, intente de vuelta.")
            
    

def login():
    print("Bienvenido a la pizzeria")
    usuario = input("Ingrese su nombre de usuario: ")
    contrasena = input("Ingrese su contraseña: ")
    
    for i in range(len(usuarios)):
        if usuarios[i][0] == usuario and usuarios[i][1] == contrasena:
            print("Bienvenido", usuario)
            return True
    else:
        print("Usuario o contraseña incorrectos.")
        return False

