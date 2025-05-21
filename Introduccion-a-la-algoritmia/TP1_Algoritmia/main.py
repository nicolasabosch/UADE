usuarios=[[0,"Nicolas", "uade2025", "admin"],[1,"Luana","uba2025","cliente","Tucuman2025"],[2,"Fran","itba2025","cliente,GeneralLopez2560"]]
tipoUsuario=["admin","cliente"]
menu=["Muzarella","Napolitana","Calabresa","Fugazzeta","Vegetariana","4 Quesos","Peperoni","Barbacoa"]
pedidos=[[0,"Nicolas",["Napolitana", 2]]]
logueado=False
iniciado=False

# def pedido(usuarioActual, menu, pedidos):
#     for i in range(len(menu)):
#         print(i,menu[i])

#     while True:
#             print("¿Qué pizza quieres pedir?")
#             for i in range(len(menu)):
#                 print(i,menu[i])
#             pizza = input("Ingrese pizza que desea: ")
#             for i in range(len(menu)):
#                 if pizza == menu[i]:
#                     print(pizza, "agregada al pedido")
#                     cantidad=input("cuantas queres pedir?")
#                     break              
#             else:
#                 print("Por favor, ingrese un número válido.")
            
            
# idPedido=len(pedidos)+1
# pedidos.append([idPedido,usuarioActual,[pizza,cantidad]])

def login():
    print("Bienvenido a la pizzeria")
    usuario = input("Ingrese su nombre de usuario: ")
    contrasena = input("Ingrese su contraseña: ")
    
    for i in range(len(usuarios)):
        if usuarios[i][0] == usuario and usuarios[i][1] == contrasena:
            print("Bienvenido", usuario)
            return True, usuarios[i][2]
    else:
        print("Usuario o contraseña incorrectos.")
        return False


while iniciado==False:
    print("Bienvenido a la pizzeria")
    logueado, usuarioActual = login()
    
    if logueado==True:
        
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
                    encontrado = False
                    for i, usuario in enumerate(usuarios):
                        if usuario[0] == usuarioEliminar:  # Checking the username at index 0
                            usuarios.pop(i)
                            print("Usuario",usuarioEliminar," eliminado con éxito.")
                            encontrado = True
                            break
                    
                    if not encontrado:
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
            
