import random

def obtener_clave_por_indice(diccionario, indice):
    claves = list(diccionario.keys())
    if 0 <= indice < len(claves):
        return claves[indice]
    return None


def listarOpcionesExtras():
    print("1. Agregar email")
    print("2. Agregar direccion")
    print("3. Agregar empresa")
    print("4. Agregar notas extra")
    print("5. Agregar usuario de redes sociales")
    print("6. Agregar fecha de cumpleaniosaños")
    print("7. Agregar otro dato extra")
    print("0. Salir")
    opcion = input("Ingrese una opcion: ")
    return opcion

def posicionExistenciaUsuario(nombre, apellido, agenda):
    """Retorna el índice del contacto con ese nombre y apellido, o -1 si no existe."""
    i = 0
    while i < len(agenda):
        if agenda[i]["nombre"] == nombre and agenda[i]["apellido"] == apellido:
            return i
        i += 1
    return -1

def validar_id_repetido(agenda, numero):
    for contacto in agenda:
        if contacto["numero"] == numero:
            return True
    return False

# Función para verificar si un valor de campo extra ya existe en la agenda
def validar_campo_extra_repetido(agenda, campo, valor):
    if valor == "":
        return False
    for contacto in agenda:
        if contacto[campo] == valor:
            return True
    return False

#las funciones estas de arriba deberian estar todas en una


def decidir_sobre_duplicado(campo, valor):
    """Pregunta al usuario si desea reingresar un campo duplicado."""
    print(f"El {campo} '{valor}' ya existe en otro contacto.")
    decision = pedir_si_no("¿Desea volver a ingresarlo? (si/no): ")
    if decision in ["si", "sí"]:
        return True  # True → el usuario quiere volver a escribir
    else:
        return False  # False → acepta dejarlo igual

# ---------- AUXILIARES DE ENTRADA Y NORMALIZACIÓN ----------
def pedir_si_no(msg):
    """Pide una respuesta 'si' o 'no' y la valida."""
    rta = input(msg).strip().lower()
    while rta not in ["si", "sí", "no"]:
        rta = input("Responda 'si' o 'no': ").strip().lower()
    return rta

def normalizador(txt):
    """Normaliza un texto: elimina espacios extra, minúsculas, y pone la primera en mayúscula."""
    return txt.strip().lower().title()

def pedirValidarTelefono():
    """Pide y valida un número de teléfono en formato internacional ."""
    # Código país (1..3 dígitos)
    cc = input("Código de país (ej: +54 o 54): ").strip()
    if len(cc) > 0 and cc[0] == "+":
        cc = cc[1:]
    while (cc.isdigit() == False) or (len(cc) < 1 or len(cc) > 3):
        print("El código de país debe tener entre 1 y 3 dígitos.")
        cc = input("Código de país (ej: +54 o 54): ").strip()
        if len(cc) > 0 and cc[0] == "+":
            cc = cc[1:]

    local = input("Número local (podés usar espacios, -, () ): ").strip()
    local = local.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
    while (local.isdigit() == False) or (len(local) < 4 or (len(cc) + len(local) > 15)):
        print("Número inválido. Debe tener solo dígitos y longitud total (+código) ≤ 15.")
        local = input("Número local (solo dígitos, separadores serán ignorados): ").strip()
        local = local.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
    return "+" + cc + local

# Función para verificar si un ID (número de contacto) ya existe

# Función para agregar nuevos contactos a la agenda
def agregar_contacto(agenda):
    ver_listado(agenda)
    datosUsuario = dict()

    # 1. Generar número único (ID)
    numero_contacto = random.randint(0, 9999)
    while validar_id_repetido(agenda, numero_contacto):
        numero_contacto = random.randint(0, 9999)

    datosUsuario["numero"]= numero_contacto

    # 2. Pedir Nombre y Apellido (obligatorios)
    nombre = input("Ingrese el nombre del contacto: ")
    while nombre == "":
        print("Debe poner un nombre al contacto")
        nombre = input("Ingrese nuevamente el nombre del contacto: ")
    
    apellido = input("Ingrese el apellido del contacto: ")
    while apellido == "":
        print("Debe poner un apellido al contacto")
        apellido = input("Ingrese nuevamente el apellido del contacto: ")
    
    nombre = normalizador(nombre)
    apellido = normalizador(apellido)
    datosUsuario["nombre"] = nombre
    datosUsuario["apellido"] = apellido

    # 3. Pedir Teléfono
    telefono = pedirValidarTelefono()
    # Verificar si el número ya existe en la agenda
    while validar_campo_extra_repetido(agenda, "telefono", telefono):
        print("El número", telefono, "ya está en la agenda.")
        decision = pedir_si_no("¿Desea usarlo igualmente para este contacto? (si/no): ")
        if decision.lower() in ["si", "sí"]:
            break
        telefono = pedirValidarTelefono()
    
    datosUsuario["telefono"] = telefono

    # 4. Pedir datos extra (opcionales)
    agregar_extras = input("¿Desea agregar datos extra al contacto? (si/no): ").lower()
    while agregar_extras not in ["si", "sí", "no"]:
        agregar_extras = input("Responda 'si' o 'no': ").lower()

    if agregar_extras.lower() in ["si", "sí"]:
        opcion = listarOpcionesExtras()
        while opcion != "0":
            if opcion == "1":
                email = input("Ingrese el email: ")
                if email.strip():  # Solo agregar si no está vacío
                    datosUsuario["mail"] = email
            elif opcion == "2":
                direccion = input("Ingrese la direccion: ")
                if direccion.strip():
                    datosUsuario["direccion"] = direccion
            elif opcion == "3":
                empresa = input("Ingrese la empresa: ")
                if empresa.strip():
                    datosUsuario["empresa"] = empresa
            elif opcion == "4":
                notas = input("Ingrese las notas: ")
                if notas.strip():
                    datosUsuario["nota"] = notas
            elif opcion == "5":
                usuario = input("Ingrese el usuario de redes sociales: ")
                if usuario.strip():
                    datosUsuario["usuario_red_social"] = usuario
            elif opcion == "6":
                cumpleaniosanios = input("Ingrese la fecha de cumpleaniosaños: ")
                if cumpleaniosanios.strip():
                    datosUsuario["cumpleanios"] = cumpleaniosanios
            elif opcion == "7":
                tipoDato = input("Ingrese el tipo de dato que desea agregar: ")
                valorDato = input("Ingrese el valor del dato: ")
                if valorDato.strip():
                    datosUsuario[tipoDato] = valorDato
            else:
                print("Opción inválida.")
            
            opcion = listarOpcionesExtras()
      
    # 5. Resuelve DUPLICADO de NOMBRE+APELLIDO
    pos_dup = posicionExistenciaUsuario(nombre, apellido, agenda)
    if pos_dup != -1:
        print("(AVISO) Ya existe un contacto con el nombre:", nombre, apellido)
        
    # 6. Agregar el nuevo contacto a la lista de diccionarios
    agenda.append(datosUsuario)
    print("Contacto agregado con éxito.")


# Función para eliminar contactos con búsqueda por nombre o apellido
def eliminar_contacto(agenda):
    if len(agenda) == 0:
        print("No hay contactos para eliminar")

    print("¿Cómo desea buscar el contacto a eliminar? 1=Por nombre 2=Por apellido")
    tipo_busqueda = input("Opción (1-2): ")

    contactos_encontrados = []
    
    if tipo_busqueda == "1":
        nombre_buscar = input("Ingrese el nombre o parte del nombre del contacto: ").lower()
        termino_busqueda = nombre_buscar
        campo_busqueda = "nombre"
    elif tipo_busqueda == "2":
        apellido_buscar = input("Ingrese el apellido o parte del apellido del contacto: ").lower()
        termino_busqueda = apellido_buscar
        campo_busqueda = "apellido"
    else:
        print("Opción inválida.")
        return

    # Buscar índices de contactos que coincidan
    i = 0
    for contacto in agenda:
        if termino_busqueda in contacto[campo_busqueda].lower():
            contactos_encontrados.append(i)
        i += 1

    if len(contactos_encontrados) > 0:
        print("\nContactos encontrados:")
        for i in contactos_encontrados:
            contacto = agenda[i]
            print(f"\nNúmero: {contacto['numero']}")
            print(f"Nombre: {contacto['nombre']}")
            print(f"Apellido: {contacto['apellido']}")
            print(f"Teléfono: {contacto['telefono']}")

        try:
            id_buscar = int(input("\nIngrese el ID (número) del contacto a eliminar: "))
        except ValueError:
            print("ID inválido.")
            return
            
        pos = -1
        i = 0
        while i < len(agenda) and pos == -1:
            if agenda[i]["numero"] == id_buscar:
                pos = i
            i += 1

        if pos != -1:
            contacto_eliminar = agenda[pos]
            nombre_eliminado = contacto_eliminar["nombre"] + " " + contacto_eliminar["apellido"]

            conf = pedir_si_no(f"¿Confirmás eliminar a {nombre_eliminado}? (si/no): ")
            if conf in ["si", "sí"]:
                agenda.pop(pos)
                print("Contacto", nombre_eliminado, "eliminado con éxito.")
            else:
                print("Operación cancelada.")
        else:
            print("Contacto no encontrado")
    else:
        print("No se encontraron contactos.")


# Función para modificar datos de un contacto con búsqueda por nombre o apellido
def modificar_contacto(agenda):
    if len(agenda) == 0:
        print("No hay contactos para modificar")
        
    print("¿Cómo desea buscar el contacto a modificar? 1=Por nombre 2=Por apellido")
    tipo_busqueda = input("Opción (1-2): ")

    contactos_encontrados = []
    
    if tipo_busqueda == "1":
        nombre_buscar = input("Ingrese el nombre o parte del nombre del contacto: ").lower()
        termino_busqueda = nombre_buscar
        campo_busqueda = "nombre"
    elif tipo_busqueda == "2":
        apellido_buscar = input("Ingrese el apellido o parte del apellido del contacto: ").lower()
        termino_busqueda = apellido_buscar
        campo_busqueda = "apellido"
    else:
        print("Opción inválida.")
        return

    # Buscar índices de contactos que coincidan
    i = 0
    for contacto in agenda:
        if termino_busqueda in contacto[campo_busqueda].lower():
            contactos_encontrados.append(i)
        i += 1

    if len(contactos_encontrados) > 0:
        print("\nContactos encontrados:")
        for i in contactos_encontrados:
            contacto = agenda[i]
            print(f"\nNúmero: {contacto['numero']}")
            print(f"Nombre: {contacto['nombre']}")
            print(f"Apellido: {contacto['apellido']}")
            print(f"Teléfono: {contacto['telefono']}")

        try:
            id_buscar = int(input("\nID del contacto a modificar: "))
        except ValueError:
            print("ID inválido.")
            return

        pos = -1
        i = 0
        while i < len(agenda) and pos == -1:
            if agenda[i]["numero"] == id_buscar:
                pos = i
            i += 1
            
        if pos == -1:
            print("Contacto no encontrado")
        else:
            contacto = agenda[pos]
            salir = False
            while not salir:
                for posicion, (key, value) in enumerate(contacto.items()):
                    print(f"{posicion} {key}: {value}")

                opcion_mod = int(input("Opción (0-"+len(contacto.keys()) +" o -1 para salir): "))
                valorKey = obtener_clave_por_indice(contacto, opcion_mod)

                nuevoValor = input(f"Cual es el nuevo valor para {valorKey}: ")
                contacto[valorKey] = nuevoValor
                    #print("Opción inválida.")
                salir=True
        
    else:
        print("No se encontraron contactos.")


def ordenar_contactos(agenda):
    """Ordena la agenda por Apellido y luego por Nombre."""
    if len(agenda) == 0:
        return
    else:
        # Usa el método sort con una clave lambda que ordena por "apellido" y luego por "nombre"
        agenda.sort(key=lambda contacto: (contacto["apellido"], contacto["nombre"]))


# Función para buscar un contacto por nombre o apellido
def buscar_contacto(agenda):
    if len(agenda) == 0:
        print("No hay contactos para buscar")

    print("¿Cómo desea buscar? 1=Por nombre 2=Por apellido")
    tipo_busqueda = input("Opción (1-2): ")

    if tipo_busqueda == "1":
        termino_buscar = input("Ingrese el nombre o parte del nombre del contacto a buscar: ").lower()
        campo_a_buscar = "nombre"
    elif tipo_busqueda == "2":
        termino_buscar = input("Ingrese el apellido o parte del apellido del contacto a buscar: ").lower()
        campo_a_buscar = "apellido"
    else:
        print("Opción inválida.")
        return

    encontrado = False
    print(f"\nBuscando contactos que contengan en {campo_a_buscar}: {termino_buscar}")

    for contacto in agenda:
        # Verificar si el texto buscado está contenido en el nombre o apellido (según corresponda)
        if termino_buscar in contacto[campo_a_buscar].lower():
            if not encontrado:
                print("\nContactos encontrados:")
                encontrado = True

            print(f"\nNúmero: {contacto['numero']}")
            print(f"Nombre: {contacto['nombre']}")
            print(f"Apellido: {contacto['apellido']}")
            print(f"Teléfono: {contacto['telefono']}")
            
            # Mostrar campos extra solo si tienen valor
            if contacto.get("documento", "").strip(): print(f"Documento: {contacto['documento']}")
            if contacto.get("mail", "").strip(): print(f"Email: {contacto['mail']}")
            if contacto.get("direccion", "").strip(): print(f"Dirección: {contacto['direccion']}")
            if contacto.get("empresa", "").strip(): print(f"Empresa: {contacto['empresa']}")
            if contacto.get("cumpleanios", "").strip(): print(f"cumpleaniosaños: {contacto['cumpleanios']}")
            if contacto.get("nota", "").strip(): print(f"Notas: {contacto['nota']}")
            if contacto.get("usuario_red_social", "").strip(): print(f"Usuario Redes: {contacto['usuario_red_social']}")
    
    if not encontrado:
        print(f"No se encontró ningún contacto que contenga ese texto en el {campo_a_buscar}.")


# Función para ver lista
def ver_listado(agenda):
    if len(agenda) == 0:
        print("No hay contactos cargados")
        return

    ordenar_contactos(agenda) # Ordenar antes de mostrar

    print("\n===========================================================")
    print("                LISTADO DE CONTACTOS ORDENADO")
    print("===========================================================")
    print(f"{'Número':<6} | {'Nombre':<15} | {'Apellido':<15} | {'Teléfono':<15}")
    print("-------|-----------------|-----------------|-----------------")
    
    for contacto in agenda:
        # Asegurar que todos los campos existan con valores por defecto
        numero = contacto.get("numero", "N/A")
        nombre = contacto.get("nombre", "N/A")
        apellido = contacto.get("apellido", "N/A")
        telefono = contacto.get("telefono", "N/A")
        
        print(f"{numero:<6} | {nombre:<15} | {apellido:<15} | {telefono:<15}")
        
        # Extras solo si hay algo
        extras = []
        if contacto.get("documento", "").strip(): 
            extras.append(f"Doc: {contacto['documento']}")
        if contacto.get("mail", "").strip(): 
            extras.append(f"Email: {contacto['mail']}")
        if contacto.get("direccion", "").strip(): 
            extras.append(f"Dir: {contacto['direccion']}")
        if contacto.get("empresa", "").strip(): 
            extras.append(f"Emp: {contacto['empresa']}")
        if contacto.get("cumpleanios", "").strip(): 
            extras.append(f"cumpleanios: {contacto['cumpleanios']}")
        if contacto.get("nota", "").strip(): 
            extras.append(f"Notas: {contacto['nota']}")
        if contacto.get("usuario_red_social", "").strip(): 
            extras.append(f"Usuario Redes: {contacto['usuario_red_social']}")

        if extras:
            print("    Extras -> " + " | ".join(extras))
            print("       |                 |                 |                 ")            
            print("-------|-----------------|-----------------|-----------------")
    print("")
    print("===========================================================")


# Función para verificar si un número está dentro de un rango
def validar_rango(elemento, primero, segundo):
    valido = False
    if elemento >= primero and elemento <= segundo:
        valido = True
    return valido

# Funcion de menu
def mostrar_menu():
    print("\n --------AGENDA DE CONTACTOS-----")
    print("1. Agregar contacto")
    print("2. Eliminar contacto")
    print("3. Modificar contacto")
    print("4. Buscar contacto")
    print("5. Ver listado de contactos")
    print("6. Salir")

# Función de selección de menú solicitada por el usuario
def seleccionar_opcion_menu():
    opcion_valida = False
    opcion = 0
    while not opcion_valida:
        try:
            opcion_input = input("Ingrese una opción (1-6): ")
            opcion = int(opcion_input)
            if validar_rango(opcion, 1, 6):
                opcion_valida = True
            else:
                print("Opción inválida. Por favor ingrese un número entre 1 y 6.")
                opcion = 0
        except ValueError:
            print("Error: Por favor, ingrese un número entero (1 a 6) para seleccionar una opción.")
            opcion = 0
    return opcion

# Funcion del menu principal
def menu_principal(opcion, agenda):
    if opcion == 1:
        agregar_contacto(agenda)
    elif opcion == 2:
        eliminar_contacto(agenda)
    elif opcion == 3:
        modificar_contacto(agenda)
    elif opcion == 4:
        buscar_contacto(agenda)
    elif opcion == 5:
        ver_listado(agenda)
    elif opcion == 6:
        print("Saliendo del programa...")

# CÓDIGO PRINCIPAL
# Inicialización de la agenda con una lista de diccionarios
agenda = [
    {
        "numero": 101, "nombre": "Juan", "apellido": "Perez", "documento": "12345678",
        "telefono": "+541112345678", "mail": "juan@gmail.com", "direccion": "",
        "empresa": "", "usuario_red_social": "", "nota": "", "cumpleanios": ""
    },
    {
        "numero": 102, "nombre": "Ana", "apellido": "Gomez", "documento": "87654321",
        "telefono": "+541156639639", "mail": "ana@gmail.com", "direccion": "",
        "empresa": "", "usuario_red_social": "", "nota": "", "cumpleanios": ""
    },
    {
        "numero": 103, "nombre": "Carlos", "apellido": "Lopez", "documento": "11223344",
        "telefono": "+541190098876", "mail": "carlos@gmail.com", "direccion": "",
        "empresa": "", "usuario_red_social": "", "nota": "", "cumpleanios": ""
    }
]

opcion = 0
print("-------Bienvenido a la Agenda de Contactos--------")
while opcion != 6:
    mostrar_menu()
    opcion = seleccionar_opcion_menu()
    

    menu_principal(opcion, agenda)