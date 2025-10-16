def diaSiguiente(dia, mes, anio):
    nuevoDia=0
    nuevoMes=0
    nuevoAnio=0
    anioBisiesto=False
    
    if anio%4==0 and (anio%100!=0 or anio%400==0):
            anioBisiesto=True
    
    while dia<1 or dia>31 or mes<1 or mes>12 or anio<1 or (mes==2 and dia>28 and anioBisiesto==False) or (mes==2 and dia>29 and anioBisiesto==True):
        print("Ingrese un dia, mes y año validos")
        dia=int(input("Ingrese el dia: "))
        mes=int(input("Ingrese el mes: "))
        anio=int(input("Ingrese el año: "))
        
        
    if mes== 1 or mes== 3 or mes== 5 or mes== 7 or mes== 8 or mes== 10 or mes== 12:
        if dia==31 and mes!=12:
            nuevoMes=mes+1
            nuevoDia=1
            nuevoAnio=anio
        elif dia==31 and mes==12:
            nuevoMes=1
            nuevoDia=1
            nuevoAnio=anio+1
        else:
            nuevoDia=dia+1
            nuevoMes=mes
            nuevoAnio=anio
            
    elif mes== 4 or mes== 6 or mes== 9 or mes== 11:
        if dia==30:
            nuevoMes=mes+1
            nuevoDia=1
            nuevoAnio=anio
        else:
            nuevoDia=dia+1
            nuevoMes=mes
            nuevoAnio=anio
    elif mes== 2:
                
            
        if dia==28 and anioBisiesto==False:
            nuevoMes=mes+1
            nuevoDia=1
            nuevoAnio=anio
        elif dia==28 and anioBisiesto==True:
            nuevoMes=mes
            nuevoDia=29
            nuevoAnio=anio
        elif dia==29 and anioBisiesto==True:
            nuevoMes=mes+1
            nuevoDia=1
            nuevoAnio=anio
            
        else:
            nuevoDia=dia+1
            nuevoMes=mes
            nuevoAnio=anio
            
    return nuevoDia, nuevoMes, nuevoAnio

dia=int(input("Ingrese el dia: "))
mes=int(input("Ingrese el mes: "))
anio=int(input("Ingrese el año: "))

d,m,a=diaSiguiente(dia,mes,anio)

print(f"El dia siguiente es: {d}/{m}/{a}")