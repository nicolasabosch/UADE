import random
import progra


def listarOpcionesExtras():
    print("1. Agregar email")
    print("2. Agregar telefono")
    print("3. Agregar direccion")
    print("4. Agregar empresa")
    print("5. Agregar notas extra")
    print("6. Agregar usuario de redes sociales")
    print("7. Agregar fecha de cumpleaños")
    print("8. Agregar otro dato extra")
    print("0. Salir")
    opcion = input("Ingrese una opcion: ")
    return opcion

def agregar_contacto(agenda):
    progra.ver_listado(agenda)
    datosUsuario = dict()

    # 1. Generar número único (ID)
    numero_contacto = random.randint(0, 9999)
    while progra.validar_id_repetido(agenda, numero_contacto):
        numero_contacto = random.randint(0, 9999)

    # 2. Pedir Nombre y Apellido (obligatorios)
    nombre = input("Ingrese el nombre del contacto: ")
    while nombre == "":
        print("Debe poner un nombre al contacto")
        nombre = input("Ingrese nuevamente el nombre del contacto: ")
    

    apellido = input("Ingrese el apellido del contacto: ")
    while apellido == "":
        print("Debe poner un apellido al contacto")
        apellido = input("Ingrese nuevamente el apellido del contacto: ")
    nombre = progra.normalizar_nombre(nombre)
    apellido = progra.normalizar_nombre(apellido)
    datosUsuario["nombre"]= nombre
    datosUsuario["apellido"]= apellido

    # 3. Pedir Teléfono
    telefono = progra.telefono_internacional()
    # Verificar si el número ya existe en la agenda
    while progra.validar_campo_extra_repetido(agenda, "telefono", telefono):
        print("El número", telefono, "ya está en la agenda.")
        decision = progra.pedir_si_no("¿Desea usarlo igualmente para este contacto? (si/no): ")
        if decision.lower() in ["si", "sí"]:
            break
        telefono = progra.telefono_internacional()

    # 4. Pedir datos extra (opcionales)

    agregar_extras = input ("¿Desea agregar datos extra al contacto? (si/no): ").lower()
    while agregar_extras not in ["si", "sí", "no"]:
        agregar_extras = input ("Responda 'si' o 'no': ").lower()

    if agregar_extras.lower() in ["si", "sí"]:
        opcion = listarOpcionesExtras()
        while opcion != "0":
            if opcion == "1":
                email = input("Ingrese el email: ")
                datosUsuario["email"] = email
            elif opcion == "2":
                telefono = input("Ingrese el telefono: ")
                datosUsuario["telefono"] = telefono                
            elif opcion == "3":
                direccion = input("Ingrese la direccion: ")
                datosUsuario["direccion"] = direccion
                
            elif opcion == "4":
                empresa = input("Ingrese la empresa: ")
                datosUsuario["empresa"] = empresa
            elif opcion == "5":
                notas = input("Ingrese las notas: ")
                datosUsuario["notas"] = notas
            elif opcion == "6":
                usuario = input("Ingrese el usuario de redes sociales: ")
                datosUsuario["usuario_red_social"] = usuario
                
            elif opcion == "7":
                cumpleanios = input("Ingrese la fecha de cumpleaños: ")
                datosUsuario["Cumpleaños"] = cumpleanios
                print("Fecha de cumpleaños agregada con éxito.")
            elif opcion=="8":
                    tipoDato= input("Ingrese el tipo de dato que desea agregar: ")                
                    valorDato = input("Ingrese el valor del dato: ")
                    datosUsuario[tipoDato] = valorDato
                    print("Dato agregado con éxito.")
            
            else:
                print("Opción inválida.")
                opcion = listarOpcionesExtras()
      
    # 6. Resuelve  DUPLICADO de NOMBRE+APELLIDO
    pos_dup = progra.pos_nombre_apellido(nombre, apellido, agenda)
    if pos_dup != -1:
        print("(AVISO) Ya existe un contacto con el nombre:", nombre, apellido)
        
    # 7. Agregar el nuevo contacto a la lista de diccionarios
    agenda.append(datosUsuario)
    print("Contacto agregado con éxito.")
