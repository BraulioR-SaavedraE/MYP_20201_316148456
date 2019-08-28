#!/usr/bin/env python
# encoding: latin1
from Entrada import obtener_lugares
from Peticiones import corre
from Salida import limpia

def main():
    """Función que hará que se ejecute el programa completo.
	Una vez que obtiene el diccionario con la información necesaria,
	hace llamadas a otras funciones para obtener el clima de las ciudades.

	El programa se cierra en cuento se detecta un problema con la API key
	o con el número de peticiones.
	"""
    lugares = obtener_lugares('Recursos/Braulio Saavedra - dataset.csv')
    for x in lugares:
    	clima = corre(lugares[x][0], lugares[x][1])
    	t = limpia(x, clima)
    	if t == "Hay un problema con la API key" or t == "Se ha superado la cantidad de peticiones que se permite":
            print(t)
    	    exit()
    	else: 
    	    print(t)

main()	#Inicio de la ejecución del programa.