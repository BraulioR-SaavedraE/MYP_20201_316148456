#!/usr/bin/env python
# encoding: latin1

def limpia(lugar, clima):
    """Función que toma los datos considerados relevantes que Open Weather Map
    brinda sobre el clima de un lugar y los procesa para su salida.
    Da aviso cuando el lugar especificado no ha sido encontrado.

    Parámetros:
    lugar: tupla con la latitud y longitud del lugar sobre el que se quiere
    conocer el clima.
    clima: información proporcionada por Open Weather Map sobre el clima
    de una localización.
    """
    
    if clima['cod'] == "400":
		return ("Las coordenadas dadas para {} son inválidas".format(lugar))
    elif clima['cod'] == 401:
        return "Hay un problema con la API key"
    elif clima['cod'] == "429":
    	return "Se ha superado la cantidad de peticiones que se permite"
    else:
	    temperatura = clima['main']['temp']
	    humedad = clima['main']['humidity']
	    nubes = clima['clouds']['all']
	    tiempo_atmosferico = clima['weather'][0]['description']
	    mensaje = "Al momento {} presenta:\nTemperatura de {} grados Fahrenheit\nHumedad de {}/100\nNubosidad de {}/100\nY {}\n"
	    return mensaje.format(lugar, temperatura, humedad, nubes, tiempo_atmosferico)