segundosTotales = int(input("Ingrese un período en segundos: "))

dias = segundosTotales // 86400
segundosRestantes = segundosTotales % 86400
horas = segundosRestantes // 3600
segundosRestantes %= 3600
minutos = segundosRestantes // 60
segundos = segundosRestantes % 60

print(segundosTotales,"segundos equivalen a ", dias, "días,", horas, "horas,", minutos, "minutos y", segundos, "segundos.")