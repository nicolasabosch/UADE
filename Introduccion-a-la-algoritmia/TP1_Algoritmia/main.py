usuarios=[[0,"Nicolas", "uade2025", "admin"],[1,"Luana","uba2025","cliente","Tucuman2025"],[2,"Fran","itba2025","cliente,GeneralLopez2560"]] # UserID, Nombre, Contraseña, Tipo de usuario, Dirección
tipoUsuario=["admin","cliente"]
idElementoCarta=[0,1,2,3,4,5,6,7] # ID de cada elemento del menú
elementosCarta=["Muzarella","Napolitana","Calabresa","Fugazzeta","Vegetariana","4 Quesos","Peperoni","Barbacoa"]
preciosCarta=[1000,1200,1300,1400,1500,1600,1700,1800]
usuarioLogueado=""
contrasenaLogueada=""
rolActual=""
#pedidos=[[0,"Nicolas",["Napolitana", 2]]]
pedidos=[]
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

def login(usuarios, rolActual):
    print("Bienvenido a la pizzeria")
    usuario = input("Ingrese su nombre de usuario: ")
    usuarioLogueado=usuario
    contrasena = input("Ingrese su contraseña: ")
    contrasenaLogueada=contrasena
    
    
    for i in range(len(usuarios)):
        if usuarios[i][0] == usuario and usuarios[i][1] == contrasena:
            rolActual=usuarios[i][3]
            print("Bienvenido", usuario)
            return True
    else:
        print("Usuario o contraseña incorrectos.")
        return False


while iniciado==False:
    print("Bienvenido a la pizzeria")
    logueado = login()
    
    if logueado==True:
        
        
            
            if rolActual=="admin":
                
                print("1.Agregar usuario, 2. Eliminar un usuario ingresa, 3. Listar usuarios, 4. Ver pedidos, 5. Salir")
                
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
            elif rolActual=="cliente":
                print("Bienvenido cliente, si quieres ver el menu ingresa 1, si quieres hacer un pedido ingresa 2, si quieres ver tus pedidos ingresa 3")
                opcion = input("Seleccione una opción: ")
                if opcion == "1":
                    print("Aquí está el menú.")
                    for i in range(len(elementosCarta)):
                        print(i,elementosCarta[i])
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
            
