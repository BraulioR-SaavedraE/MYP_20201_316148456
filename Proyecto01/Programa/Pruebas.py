#!/usr/bin/env python
# encoding: latin1
from Entrada import obtener_lugares
from Salida import limpia
from Peticiones import corre
from Peticiones import solicita_clima

def lugares_repetidos():
    """Prueba unitaria que verifica que se hayan distribuido correctamente las
    ciudades para así evitar peticiones repetidas. 
    El diccionario usado para guardar las ciudades no guarda elementos repetidos, 
    así que lo que se busca probar aquí es que se haya elegido de manera adecuada
    la llave y el valor de las entradas del mismo.
    """

    lugares = obtener_lugares('Recursos/Prueba01.csv')

    x = 0
    for y in lugares:
        x += 1
    assert x == 2, "¡Sólo había dos direcciones en el archivo!"
    print("Se encontraron {} localizaciones distintas. Prueba completada satisfactoriamente").format(x)

def lugares_inexistentes():
	"""Prueba unitaria que brinda un archivo con coordenadas inválidas y verifica
	que el programa pueda filtrarlas y avisar del error."""

	lugares = obtener_lugares('Recursos/Prueba02.csv')
	for x in lugares:
	    clima = corre(lugares[x][0], lugares[x][1])
        r = limpia('INEXISTENTE', clima)

	assert r == "Las coordenadas dadas para INEXISTENTE son inválidas", "¡No se reporta la petición sobre una ciudad inexistente!"
	print("Se detectaron correctamente coordenadas inválidas. Prueba superada")

def API_key_invalida():
    """Prueba unitaria que da una API key inválida y se asegura de que el programa advierta el error
	y acabe su ejecución."""
    
    lugares = obtener_lugares('Recursos/Prueba01.csv')

    y = ""
    for x in lugares:
        y = limpia(x, solicita_clima("0", lugares[x][0], lugares[x][1]))

    assert y == "Hay un problema con la API key", "¡No se manejó el error que produce la API key inválida!"
    print("Se detectó la API key inválida. Prueba terminada con exitosamente")


lugares_repetidos()
lugares_inexistentes()
API_key_invalida()
