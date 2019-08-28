#!/usr/bin/env python
# encoding: latin1
import configparser # Módulo que normalmente requiere ser descargado e instalado.
import requests	# Módulo que normalmente requiere ser descargado e instalado.

def configura_API_key():
    """Función que lee la API key de un archivo especificado.
    El código que se usó como base se puede encontrar en: https://code-maven.com/openweathermap-api-using-python

    Excepciones:
    FileNotFoundException si tiene problemas para leer o encontrar el archivo .init
    donde la clave debería estar alojada. Termina el programa.

    Regresa la API key recuperada del archivo leído.
    """

    config = configparser.ConfigParser()
    try:
	    config.read('Recursos/config.ini')
    except FileNotFoundException:
		print("Hay un problema con el archivo que contiene la API key")
		exit()
    else:
	    return config['openweathermap']['api']

def solicita_clima(API_key, latitud, longitud):
	"""Función que hace las peticiones a Open Weather Map.
	El código que se usó como base se puede encontrar en: https://code-maven.com/openweathermap-api-using-python

	Parámetros:
	API_key: API key para poder hacer las peticiones.
	latitud: latitud del lugar del que se quiere saber el clima.
	longitud: longitud del lugar sobre el cual se quiere conocer el clima.
	"""

	url = "http://api.openweathermap.org/data/2.5/weather?lat={}&lon={}&appid={}".format(latitud, longitud, API_key)
	r = requests.get(url)

	return r.json()

def corre(latitud, longitud):
    """Función que manda a obtener la API key y hace que se solicite el clima de una localización.

    Parámetros:
    latitud: latitud de la localización sobre la cual se quiere conocer el clima.
    longitud: longitud del lugar del que se quiere saber el clima.
    """

    API_key = configura_API_key()
    clima = solicita_clima(API_key, latitud, longitud)

    return clima