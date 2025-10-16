#GRUPO 1-TP (Aplicacion de gestion de contactos)
 
import random

# Función para agregar nuevos contactos a la agenda
def agregar_contacto(numeros, nombres, documentos, telefonos):
    ver_listado(numeros, nombres, documentos, telefonos)
    numero_contacto = random.randint(0, 9999)
    while validar_repetido(numeros, numero_contacto) == 1:
        numero_contacto = random.randint(0, 9999)

    nombre = input("Ingrese el nombre y apellido del contacto: ")
    while nombre == "": 
        print("Debe poner un nombre al contacto ")
        nombre = input("Ingrese nuevamente el nombre y apellido del contacto: ")
    nombre = nombre.title()

    documento = input("Ingrese el número de documento (00000000 a 99999999): ")
    documento = documento.zfill(8)
    while validar_repetido(documentos, documento) == 1 or validar_rango(int(documento), 0, 99999999) == 0:
        print("Documento ya ingresado o fuera del rango permitido")
        documento = input("Ingrese nuevamente el número de documento: ")
        documento = documento.zfill(8)  

    telefono = int(input("Ingrese el número de teléfono (100000 a 9999999999) o -1 para cancelar: "))
    while telefono != -1 and validar_rango(telefono, 100000, 9999999999) == 0:
        print("Número de teléfono fuera de rango permitido")
        telefono = int(input("Ingrese nuevamente el número de teléfono: "))

    if telefono != -1:
        numeros.append(numero_contacto)
        nombres.append(nombre)
        documentos.append(documento)
        telefonos.append(telefono)
        print("Contacto agregado con éxito.")
    else:
        print("Operación cancelada")

# Función para eliminar contactos por ID
def eliminar_contacto(numeros, nombres, documentos, telefonos):
    if len(nombres) == 0:
        print("No hay contactos para eliminar")
    else:
        print("Contactos disponibles:")
        ver_listado(numeros, nombres, documentos, telefonos)
        id_buscar = int(input("Ingrese el ID (número) del contacto a eliminar: "))
        i = 0
        encontrado = False
        while i < len(numeros) and encontrado == False: 
            if numeros[i] == id_buscar:
                nombre_eliminado = nombres[i]
                numeros.remove(numeros[i])
                nombres.remove(nombres[i])
                documentos.remove(documentos[i])
                telefonos.remove(telefonos[i])
                print("Contacto", nombre_eliminado, "eliminado con éxito.")
                encontrado = True
            i = i + 1
        if encontrado == False:
            print("Contacto no encontrado")

# Función para modificar datos de un contacto existente
def modificar_contacto(numeros, nombres, documentos, telefonos):
    if len(nombres) == 0:
        print("No hay contactos para modificar")
    else:
        ver_listado(numeros, nombres, documentos, telefonos)
        id_buscar = int(input("ID del contacto a modificar: "))
        i = 0
        pos = -1
        while i < len(numeros) and pos == -1:
            if numeros[i] == id_buscar:
                pos = i
            i = i+1
        if pos == -1:
            print("Contacto no encontrado")
        else:
            print("\n¿Qué desea cambiar? 1=Nombre  2=Documento  3=Teléfono")
            opcion = input("Opción (1-3): ")
            if opcion == "1":
                nuevo_nombre = input("Nuevo nombre: ")
                nombres[pos] = nuevo_nombre.title()
                print("Nombre actualizado.")
            elif opcion == "2":
                nuevo_doc = input("Nuevo documento: ")
                while validar_repetido(documentos, nuevo_doc.zfill(8)) == 1:
                    print("Documento existente o fuera del rango permitido")
                    nuevo_doc = input("Ingrese nuevamente el número de documento: ")
                    nuevo_doc = nuevo_doc.zfill(8)

                documentos[pos] = nuevo_doc.zfill(8)
                print("Documento actualizado.")
            elif opcion == "3":
                telefonos[pos] = int(input("Nuevo teléfono: "))
                print("Teléfono actualizado.")
            else:
                print("Opción inválida.")

# Función contar total de contactos
def contar_contactos(nombres):
    total = len(nombres)
    if total == 0:
        print("No hay contactos en la agenda")
    elif total == 1:
        print("Hay 1 contacto en la agenda")
    else:
        print(f"Hay {total} contactos en la agenda")

# Función para buscar un contacto usando diccionario
def buscar_contacto(numeros, nombres, documentos, telefonos):
    if len(nombres) == 0:
        print("No hay contactos para buscar")
    else:
        agenda_dict = {}
        i = 0
        while i < len(nombres):
            agenda_dict[nombres[i]] = (numeros[i], documentos[i], telefonos[i])
            i = i + 1

        nombre_buscar = input("Ingrese el nombre del contacto a buscar: ").title()
        if nombre_buscar in agenda_dict:
            numero, documento, telefono = agenda_dict[nombre_buscar]
            print("\nContacto encontrado:")
            print(f"Número: {numero}")
            print(f"Contacto: {nombre_buscar}")
            print(f"Documento: {documento}")
            print(f"Teléfono: {telefono}")
        else:
            print("No se encontró ningún contacto con ese nombre.")

# Función para verificar si un elemento ya existe en una lista
def validar_repetido(lista, nuevo):
    i = 0
    repetido = 0
    while i < len(lista):
        if lista[i] == nuevo:
            repetido = 1
        i = i + 1
    return repetido

# Función para verificar si un número está dentro de un rango
def validar_rango(elemento, primero, segundo):
    valido = 0
    if elemento >= primero and elemento <= segundo:
        valido = 1
    return valido

# Función para ordenar contactos alfabéticamente
def ordenar_contactos(numeros, nombres, documentos, telefonos):
    if len(nombres) == 0:
        print("No hay contactos para ordenar")
    else:
        indices_ordenados = sorted(range(len(nombres)), key=lambda i: nombres[i])
        numeros[:] = [numeros[i] for i in indices_ordenados]
        nombres[:] = [nombres[i] for i in indices_ordenados]
        documentos[:] = [documentos[i] for i in indices_ordenados]
        telefonos[:] = [telefonos[i] for i in indices_ordenados]

# Función para ver lista 
def ver_listado(numeros, nombres, documentos, telefonos):
    if len(nombres) == 0:
        print("No hay contactos cargados")
    else:
        ordenar_contactos(numeros, nombres, documentos, telefonos)
        print("\nListado de contactos:")
        print(f"{'Número':<6} | {'Nombre':<20} | {'Documento':<10} | {'Teléfono':<12}")
        print("-------|----------------------|------------|------------")
        i = 0
        while i < len(nombres):
            print(f"{numeros[i]:<6} | {nombres[i]:<20} | {documentos[i]:<10} | {telefonos[i]:<12}")
            i = i+1

# MENÚ
def mostrar_menu():
    print("\n --------AGENDA DE CONTACTOS-----")
    print("1. Agregar contacto")
    print("2. Eliminar contacto") 
    print("3. Modificar contacto")
    print("4. Contar contactos")
    print("5. Buscar contacto")  
    print("6. Ver listado de contactos")
    print("7. Salir")

def menu_principal(opcion, numeros, nombres, documentos, telefonos):
    if opcion == 1:
        agregar_contacto(numeros, nombres, documentos, telefonos)
    elif opcion == 2:
        eliminar_contacto(numeros, nombres, documentos, telefonos)
    elif opcion == 3:
        modificar_contacto(numeros, nombres, documentos, telefonos)
    elif opcion == 4:
        contar_contactos(nombres)
    elif opcion == 5:
        buscar_contacto(numeros, nombres, documentos, telefonos)
    elif opcion == 6:
        ver_listado(numeros, nombres, documentos, telefonos)
    elif opcion == 7:
        print("Saliendo del programa...")

# CÓDIGO PRINCIPAL CON DATOS HARDCODEADOS
numeros = [101, 102, 103]
nombres = ["Juan Perez", "Ana Gomez", "Carlos López"]
documentos = ["12345678", "87654321", "11223344"]
telefonos = [1122334455, 2233445566, 3344556677]

opcion = 0
print("-------Bienvenido a la Agenda de Contactos--------")
while opcion != 7:
    mostrar_menu()
    opcion = int(input("Ingrese una opción (1-7): "))
    while validar_rango(opcion, 1, 7) == 0:
        print("Opción inválida. Por favor ingrese un número entre 1 y 7.")
        opcion = int(input("Ingrese una opción (1-7): "))
    menu_principal(opcion, numeros, nombres, documentos, telefonos)