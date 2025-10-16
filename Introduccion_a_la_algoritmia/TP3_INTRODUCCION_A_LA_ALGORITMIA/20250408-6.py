encuadernacionRustica=5000 # De 0 a 300 paginas
encuadernacionTela=6200 # De 300 paginas a 600 paginas
encuadernacionEspecial=9560 # De 600 paginas para arriba
precioCuaderno=0
paginasCuaderno=int(input("Cuantas paginas tiene el cuaderno? "))

if paginasCuaderno<=300:
    precioCuaderno=paginasCuaderno *32
    precioCuaderno+=encuadernacionRustica
    print("el cuaderno costara: ", precioCuaderno)

elif paginasCuaderno >300 and paginasCuaderno < 600:
    precioCuaderno=paginasCuaderno *32
    precioCuaderno+=encuadernacionTela
    print("el cuaderno costara: ", precioCuaderno)


else:
    precioCuaderno=paginasCuaderno *32
    precioCuaderno+=encuadernacionEspecial
    print("el cuaderno costara:", precioCuaderno)






