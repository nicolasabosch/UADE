horas=int(input("que hora es?: "))
minutos=int(input("que minuto es?: "))

fullTime=input("El formato horario es completo o parcial?")

if fullTime=="parcial":
    fullTime=False
else:
    fullTime=True


if fullTime==False:
    if horas<=12 and horas >=0:
        
        esPM=input("Es AM o PM")
        if esPM=="am":
            esPM=False
        else:
            esPM=True
        if minutos>60:
            print("El formato de los minutos esta mal")
        print("El formato de las horas esta bien.")
    else:
        print("El formato horario esta mal")
        
else:    
    if horas<24 and horas>=0:
        print("El formato de las horas esta bien")
    if minutos>60:
        print("El formato de los minutos esta mal")
    
    else:
        print("El formato de los minutos esta bien")
    