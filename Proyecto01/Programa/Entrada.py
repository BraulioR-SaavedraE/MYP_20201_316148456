#!/usr/bin/env python
# encoding: latin1
import csv	# Módulo necesario para poder leer las columnas del archivo csv.

def obtener_lugares(nombre):
    """Función que lee un archivo csv en una ruta especificada y captura los
	resultados de ciertas columnas.
	
	Paramétros:
	nombre: ruta del archivo csv.
	
	Excepciones: 
	FileNotFoundError cuando no se encuentra el archivo csv.

	Regresa un diccionario con los datos extraídos del csv.
	"""

    lugares = {}
    try:
        with open(nombre, 'r') as my_archivo:
            reader = csv.reader(my_archivo)
            next(my_archivo)	#Eliminamos la primera línea que contiene información indeseable.
            for row in reader:
                lugares[row[0]] = row[2], row[3]
                lugares[row[1]] = row[4], row[5]
    except FileNotFoundError:
    	print("No se pudo abrir el archivo especificado")
    	sys.exit()
    else:
        return lugares