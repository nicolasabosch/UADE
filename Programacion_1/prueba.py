from tracemalloc import stop


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

#print(len(agenda[0].keys()))

def obtener_clave_por_indice(diccionario, indice):
    claves = list(diccionario.keys())
    if 0 <= indice < len(claves):
        return claves[indice]
    return None



contacto = agenda[0]
indice = 1

nombre_clave=obtener_clave_por_indice(agenda[0], indice)

print( nombre_clave)



# For loop to print each key from the first dictionary
