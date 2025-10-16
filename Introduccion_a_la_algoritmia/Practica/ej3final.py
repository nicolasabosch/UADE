competidores= [1,2,3,4]
tiempos= []

for i in range(len(competidores)):
    print(f"Ingrese el tiempo del competidor {competidores[i]} en formato horas, minutos y segundos.")
    hs=int(input("Ingrese el tiempo en horas del competidor: "))
    mm=int(input("Ingrese el tiempo en minutos del competidor: "))
    ss=int(input("Ingrese el tiempo en segundos del competidor: "))
    tiempos.append(hs*3600 + mm*60 + ss)

def tiempoMasRapido(competidores, tiempos):
    tiempoRapido=0
    competidor=0
    for i in range(len(tiempos)):
        if tiempos[i]<tiempoRapido or tiempoRapido==0:
            tiempoRapido=tiempos[i]
            competidor=competidores[i]

    print(f"El competidor {competidor} es el mas rapido con un tiempo de {tiempoRapido//3600} horas, {(tiempoRapido%3600)//60} minutos y {tiempoRapido%60} segundos.")

tiempoMasRapido(competidores, tiempos)

