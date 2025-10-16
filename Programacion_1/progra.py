import random
# La agenda principal es una lista de diccionarios.
# Cada diccionario representa un contacto.

# --- Helpers de duplicados ---

def listarOpcionesExtras():
    print("1. Agregar email")
    print("2. Agregar direccion")
    print("3. Agregar empresa")
    print("4. Agregar notas extra")
    print("5. Agregar usuario de redes sociales")
    print("6. Agregar fecha de cumpleaños")
    print("7. Agregar otro dato extra")
    print("0. Salir")
    opcion = input("Ingrese una opcion: ")
    return opcion

def pos_nombre_apellido(nombre, apellido, agenda):
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

def generar_nombre_unico(nombre_base, apellido, agenda):
    """Genera un nombre único añadiendo (1), (2), etc., si ya existe la dupla nombre+apellido."""
    # si la dupla no existe, devolvés tal cual
    if pos_nombre_apellido(nombre_base, apellido, agenda) == -1:
        return nombre_base
    # si existe, agregás (1), (2), ...
    n = 1
    while True:
        nombre_temp = nombre_base + "(" + str(n) + ")"
        if pos_nombre_apellido(nombre_temp, apellido, agenda) == -1:
            return nombre_temp
        n += 1


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

def telefono_internacional():
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
    datosUsuario["nombre"]= nombre
    datosUsuario["apellido"]= apellido

    # 3. Pedir Teléfono
    telefono = telefono_internacional()
    # Verificar si el número ya existe en la agenda
    while validar_campo_extra_repetido(agenda, "telefono", telefono):
        print("El número", telefono, "ya está en la agenda.")
        decision = pedir_si_no("¿Desea usarlo igualmente para este contacto? (si/no): ")
        if decision.lower() in ["si", "sí"]:
            break
        telefono = telefono_internacional()
    datosUsuario["telefono"] = telefono

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
                direccion = input("Ingrese la direccion: ")
                datosUsuario["direccion"] = direccion
                print("Dirección agregada con éxito.")
                opcion = listarOpcionesExtras()
            elif opcion == "3":
                empresa = input("Ingrese la empresa: ")
                datosUsuario["empresa"] = empresa
                print("Empresa agregada con éxito.")
                opcion = listarOpcionesExtras()
            elif opcion == "4":
                notas = input("Ingrese las notas: ")
                datosUsuario["notas"] = notas
                print("Notas agregadas con éxito.")
                opcion = listarOpcionesExtras()
            elif opcion == "5":
                usuario = input("Ingrese el usuario de redes sociales: ")
                datosUsuario["usuario_red_social"] = usuario
                print("Usuario de redes sociales agregado con éxito.")
                opcion = listarOpcionesExtras()
            elif opcion == "6":
                cumpleanios = input("Ingrese la fecha de cumpleaños: ")
                datosUsuario["Cumpleaños"] = cumpleanios
                print("Fecha de cumpleaños agregada con éxito.")
                opcion = listarOpcionesExtras()
            elif opcion=="7":
                    tipoDato= input("Ingrese el tipo de dato que desea agregar: ")                
                    valorDato = input("Ingrese el valor del dato: ")
                    datosUsuario[tipoDato] = valorDato
                    print("Dato agregado con éxito.")
                    opcion = listarOpcionesExtras()
            else:
                print("Opción inválida.")
                opcion = listarOpcionesExtras()
      
    # 6. Resuelve  DUPLICADO de NOMBRE+APELLIDO
    pos_dup = pos_nombre_apellido(nombre, apellido, agenda)
    if pos_dup != -1:
        print("(AVISO) Ya existe un contacto con el nombre:", nombre, apellido)
        
    # 7. Agregar el nuevo contacto a la lista de diccionarios
    agenda.append(datosUsuario)
    print("Contacto agregado con éxito.")

    ver_listado(agenda)
    datosUsuario = dict()

    # 1. Generar número único (ID)
    numero_contacto = random.randint(0, 9999)
    while validar_id_repetido(agenda, numero_contacto):
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
    nombre = normalizador(nombre)
    apellido = normalizador(apellido)
    datosUsuario["nombre"]= nombre
    datosUsuario["apellido"]= apellido

    # 3. Pedir Teléfono
    telefono = telefono_internacional()
    # Verificar si el número ya existe en la agenda
    while validar_campo_extra_repetido(agenda, "telefono", telefono):
        print("El número", telefono, "ya está en la agenda.")
        decision = pedir_si_no("¿Desea usarlo igualmente para este contacto? (si/no): ")
        if decision.lower() in ["si", "sí"]:
            break
        telefono = telefono_internacional()

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
    pos_dup = pos_nombre_apellido(nombre, apellido, agenda)
    if pos_dup != -1:
        print("(AVISO) Ya existe un contacto con el nombre:", nombre, apellido)
        
    # 7. Agregar el nuevo contacto a la lista de diccionarios
    agenda.append(datosUsuario)
    print("Contacto agregado con éxito.")

    ver_listado(agenda)

    # 1. Generar número único (ID)
    numero_contacto = random.randint(0, 9999)
    while validar_id_repetido(agenda, numero_contacto):
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

    nombre = normalizador(nombre)
    apellido = normalizador(apellido)

    # 3. Pedir Teléfono
    telefono = telefono_internacional()
    # Verificar si el número ya existe en la agenda
    while validar_campo_extra_repetido(agenda, "telefono", telefono):
        print("El número", telefono, "ya está en la agenda.")
        decision = pedir_si_no("¿Desea usarlo igualmente para este contacto? (si/no): ")
        if decision == "si" or decision == "sí":
            break
        telefono = telefono_internacional()

    # 4. Pedir datos extra (opcionales)
    doc_extra = ""
    mail_extra = ""
    dir_extra = ""
    emp_extra = ""
    notas_extra = ""
    usu_extra = ""
    cumple_extra = ""

    agregar_extras = input ("¿Desea agregar datos extra al contacto? (si/no): ").lower()
    while agregar_extras not in ["si", "sí", "no"]:
        agregar_extras = input ("Responda 'si' o 'no': ").lower()

    if agregar_extras in ["si", "sí"]:
        mail_extra = input ("Ingresar email (o dejar vacio): ")
        cumple_extra = input ("Ingresar fecha de cumpleaños (o dejar vacio): ")
        doc_extra = input ("Ingresar numero de documento (o dejar vacio): ")
        dir_extra = input ("Ingresar direccion (o dejar vacio): ")
        emp_extra = input("Ingresar empresa (o dejar vacio): ")
        notas_extra = input("Ingresar notas extra (o dejar vacio): ")
        usu_extra = input ("Ingresar el nombre de usuario de sus redes sociales (o dejar vacio): ")

    # 5. Validar y reingresar (si se desea) campos duplicados entre los extras
    if doc_extra != "" and validar_campo_extra_repetido(agenda, "documento", doc_extra):
        if decidir_sobre_duplicado("documento", doc_extra):
            doc_extra = input("Ingrese otro documento (o vacío): ")
    
    if mail_extra != "" and validar_campo_extra_repetido(agenda, "mail", mail_extra):
        if decidir_sobre_duplicado("email", mail_extra):
            mail_extra = input("Ingrese otro email (o vacío): ")

    if usu_extra != "" and validar_campo_extra_repetido(agenda, "usuario_red_social", usu_extra):
        if decidir_sobre_duplicado("usuario", usu_extra):
            usu_extra = input("Ingrese otro usuario (o vacío): ")
    
    # 6. Resuelve  DUPLICADO de NOMBRE+APELLIDO
    pos_dup = pos_nombre_apellido(nombre, apellido, agenda)
    nuevo_contacto = {
        "numero": numero_contacto,
        "nombre": nombre,
        "apellido": apellido,
        "documento": doc_extra,
        "telefono": telefono,
        "mail": mail_extra,
        "direccion": dir_extra,
        "empresa": emp_extra,
        "usuario_red_social": usu_extra,
        "nota": notas_extra,
        "cumple": cumple_extra,
    }

    if pos_dup != -1:
        print("Ya existe un contacto con el nombre:", nombre, apellido)
        decision = input("¿Desea reemplazarlo (R) o agregar como nuevo (N)? ").strip().lower()
        while decision not in ["r", "n"]:
            decision = input("Responda 'R' (reemplazar) o 'N' (nuevo): ").strip().lower()

        if decision == "r":
            # Reemplazar el contacto existente en la posición 'pos_dup'
            nuevo_contacto["numero"] = agenda[pos_dup]["numero"] # Conservar el ID original
            agenda[pos_dup] = nuevo_contacto
            print("Contacto existente reemplazado (por nombre y apellido).")
            return

        else:
            # Generar nombre único si se elige agregar como nuevo
            nombre_unico = generar_nombre_unico(nombre, apellido, agenda)
            nuevo_contacto["nombre"] = nombre_unico
            print("Se guardará como:", nombre_unico, apellido)

    # 7. Agregar el nuevo contacto a la lista de diccionarios
    agenda.append(nuevo_contacto)
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
                print(f"\nModificando contacto: {contacto['nombre']} {contacto['apellido']} (ID: {contacto['numero']})")
                print("¿Qué desea cambiar?")
                print(f"1. Nombre (Actual: {contacto['nombre']})")
                print(f"2. Apellido (Actual: {contacto['apellido']})")
                print(f"3. Documento (Actual: {contacto['documento']})")
                print(f"4. Teléfono (Actual: {contacto['telefono']})")
                print(f"5. Email (Actual: {contacto['mail']})")
                print(f"6. Dirección (Actual: {contacto['direccion']})")
                print(f"7. Empresa (Actual: {contacto['empresa']})")
                print(f"8. Cumpleaños (Actual: {contacto['cumple']})")
                print(f"9. Notas (Actual: {contacto['nota']})")
                print(f"10. Usuario Red Social (Actual: {contacto['usuario_red_social']})")
                print("0. Terminar")

                opcion_mod = input("Opción (0-10): ")

                if opcion_mod == "1":
                    nuevo = input(f"Nuevo nombre: ")
                    while nuevo == "":
                        print("El nombre no puede estar vacío")
                        nuevo = input("Nuevo nombre: ")
                    temp_nombre = normalizador(nuevo)
                    if pos_nombre_apellido(temp_nombre, contacto["apellido"], agenda) != -1 and temp_nombre != contacto["nombre"]:
                        print(f"Ya existe un contacto llamado {temp_nombre} {contacto['apellido']}. Se le agregará una numeración.")
                        contacto["nombre"] = generar_nombre_unico(temp_nombre, contacto["apellido"], agenda)
                    else:
                        contacto["nombre"] = temp_nombre
                    print("Nombre actualizado.")

                elif opcion_mod == "2":
                    nuevo = input(f"Nuevo apellido: ")
                    while nuevo == "":
                        print("El apellido no puede estar vacío")
                        nuevo = input("Nuevo apellido: ")
                    temp_apellido = normalizador(nuevo)
                    if pos_nombre_apellido(contacto["nombre"], temp_apellido, agenda) != -1 and temp_apellido != contacto["apellido"]:
                        print(f"Ya existe un contacto llamado {contacto['nombre']} {temp_apellido}.")
                    contacto["apellido"] = temp_apellido
                    print("Apellido actualizado.")

                elif opcion_mod == "3":
                    nuevo = input(f"Nuevo documento. Enter para vacío: ")
                    while nuevo != "" and nuevo != contacto["documento"] and validar_campo_extra_repetido(agenda, "documento", nuevo):
                        print("Documento ya ingresado en otro contacto.")
                        nuevo = input("Ingrese otro documento (o Enter para vacío): ")
                    contacto["documento"] = nuevo
                    print("Documento actualizado.")
                    
                elif opcion_mod == "4":
                    print("Ingrese nuevo teléfono:")
                    nuevo_tel = telefono_internacional()
                    while nuevo_tel != contacto["telefono"] and validar_campo_extra_repetido(agenda, "telefono", nuevo_tel):
                        print("Teléfono ya ingresado en otro contacto.")
                        conf_uso = pedir_si_no("¿Desea usarlo igualmente? (si/no): ")
                        if conf_uso in ["si", "sí"]:
                            break
                        print("Ingrese otro teléfono:")
                        nuevo_tel = telefono_internacional()
                    contacto["telefono"] = nuevo_tel
                    print ("Teléfono actualizado")

                elif opcion_mod == "5":
                    nuevo = input(f"Nuevo email. Enter para vacío: ")
                    while nuevo != "" and nuevo != contacto["mail"] and validar_campo_extra_repetido(agenda, "mail", nuevo):
                        print("Email ya ingresado en otro contacto.")
                        nuevo = input("Ingrese otro email (o Enter para vacío): ")
                    contacto["mail"] = nuevo
                    print("Email actualizado.")

                elif opcion_mod == "6":
                    contacto["direccion"] = input(f"Nueva dirección. Enter para vacío: ")
                    print("Dirección actualizada.")

                elif opcion_mod == "7":
                    contacto["empresa"] = input(f"Nueva empresa. Enter para vacío: ")
                    print("Empresa actualizada.")

                elif opcion_mod == "8":
                    contacto["cumple"] = input(f"Nuevo cumpleaños. Enter para vacío: ")
                    print("Cumpleaños actualizado.")

                elif opcion_mod == "9":
                    contacto["nota"] = input(f"Nuevas notas. Enter para vacío: ")
                    print("Notas actualizadas.")
                
                elif opcion_mod == "10":
                    nuevo = input(f"Nuevo usuario de red social. Enter para vacío: ")
                    while nuevo != "" and nuevo != contacto["usuario_red_social"] and validar_campo_extra_repetido(agenda, "usuario_red_social", nuevo):
                        print("Usuario de red social ya ingresado en otro contacto.")
                        nuevo = input("Ingrese otro usuario (o Enter para vacío): ")
                    contacto["usuario_red_social"] = nuevo
                    print("Usuario Red Social actualizado.")

                elif opcion_mod == "0":
                    salir = True
                else:
                    print("Opción inválida.")
    else:
        print("No se encontraron contactos.")


def ordenar_contactos(agenda):
    """Ordena la agenda por Apellido y luego por Nombre."""
    if len(agenda) == 0:
        return
    else:
        # Usa el método sort con una clave lambda que ordena por "apellido" y luego por "nombre"
        agenda.sort(key=lambda contacto: (contacto["nombre"], contacto["apellido"]))


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
            if contacto.get("documento", ""): print(f"Documento: {contacto['documento']}")
            if contacto.get("mail", ""): print(f"Email: {contacto['mail']}")
            if contacto.get("direccion", ""): print(f"Dirección: {contacto['direccion']}")
            if contacto.get("empresa", ""): print(f"Empresa: {contacto['empresa']}")
            if contacto.get("cumple", ""): print(f"Cumpleaños: {contacto['cumple']}")
            if contacto.get("nota", ""): print(f"Notas: {contacto['nota']}")
            if contacto.get("usuario_red_social", ""): print(f"Usuario Redes: {contacto['usuario_red_social']}")
    
    if not encontrado:
        print(f"No se encontró ningún contacto que contenga ese texto en el {campo_a_buscar}.")


# Función para ver lista
def ver_listado(agenda):
    if len(agenda) == 0:
        print("No hay contactos cargados")

    ordenar_contactos(agenda) # Ordenar antes de mostrar

    print("\n===========================================================")
    print("                LISTADO DE CONTACTOS ORDENADO")
    print("===========================================================")
    print(f"{'Número':<6} | {'Nombre':<15} | {'Apellido':<15} | {'Teléfono':<15}")
    print("-------|-----------------|-----------------|-----------------")
    
    for contacto in agenda:
        print(f"{contacto['numero']:<6} | {contacto['nombre']:<15} | {contacto['apellido']:<15} | {contacto['telefono']:<15}")
        
        # Extras solo si hay algo
        extras = []
        if contacto.get("documento", ""): extras.append(f"Doc: {contacto['documento']}")
        if contacto.get("mail", ""): extras.append(f"Email: {contacto['mail']}")
        if contacto.get("direccion", ""): extras.append(f"Dir: {contacto['direccion']}")
        if contacto.get("empresa", ""): extras.append(f"Emp: {contacto['empresa']}")
        if contacto.get("cumple", ""): extras.append(f"Cumple: {contacto['cumple']}")
        if contacto.get("nota", ""): extras.append(f"Notas: {contacto['nota']}")
        if contacto.get("usuario_red_social", ""): extras.append(f"Usuario Redes: {contacto['usuario_red_social']}")

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
        "empresa": "", "usuario_red_social": "", "nota": "", "Cumpleaños": ""
    },
    {
        "numero": 102, "nombre": "Ana", "apellido": "Gomez", "documento": "87654321",
        "telefono": "+541156639639", "mail": "ana@gmail.com", "direccion": "",
        "empresa": "", "usuario_red_social": "", "nota": "", "Cumpleaños": ""
    },
    {
        "numero": 103, "nombre": "Carlos", "apellido": "Lopez", "documento": "11223344",
        "telefono": "+541190098876", "mail": "carlos@gmail.com", "direccion": "",
        "empresa": ""
    }
]

opcion = 0
print("-------Bienvenido a la Agenda de Contactos--------")
while opcion != 6:
    mostrar_menu()
    opcion = seleccionar_opcion_menu()
    

    menu_principal(opcion, agenda)