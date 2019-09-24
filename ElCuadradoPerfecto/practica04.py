import cv2
import os
import numpy as np
import pickle
import random

class Practica04:
    """Clase principal de la práctica 04"""
    IMG_SIZE = 0
    IMAGES = list()

    def main(self, carpeta, tamano):
        """Función principal"""
        self.IMG_SIZE = tamano

        # agarra todas las imagenes y se les asigna una etiqueta a cada una de
        # ellas dependiendo de la carpeta en la que estaban originalmente
        for idx, subcarpeta in enumerate(self.iterar_carpeta(carpeta)):
            imgs = [ self.reescalar_imagen(img) for img in
                    self.iterar_imagenes(subcarpeta) ]
            for img in imgs:
                self.listaImagenEtiqueta(self.etiqueta(img, idx))

        # permuta el orden de las imagenes, las separa en vectores de
        # caracteristicas (numpy ndarray) y labels y los guarda en formato
        # pickle en las rutas determinadas
        self.IMAGES = self.shuffle(self.IMAGES)
        features, labels = self.aplicaNumpy(self.IMAGES)
        self.escribirArchivo((features, labels))

        # se creara

        # pruebas para aplicaNumpy():
        #self.IMG_SIZE = tamano
        #img = cv2.imread('prueba.jpg', cv2.IMREAD_GRAYSCALE)
        #img = cv2.resize(img, (self.IMG_SIZE, self.IMG_SIZE))
        #listaTuplas = []
        #for i in range(0,150):
        #    listaTuplas.append((img, i))
        #tuplaImagenesEtiquetas = self.aplicaNumpy(listaTuplas)
        #print(tuplaImagenesEtiquetas[0].shape)

    def iterar_carpeta(self, carpeta): # funcion 1
        '''Regresa iterable con cada subcarpeta de la carpeta especificada'''
        return [ f.path for f in os.scandir(carpeta) if f.is_dir() ]

    def iterar_imagenes(self, carpeta): # funcion 2
        '''Regresa iterable con cada imagen de la carpeta especificada'''
        is_image = lambda path: any([ x in path for x in [ '.png', '.jpg' ] ])
        leer_imagen = lambda path: cv2.imread(path, cv2.IMREAD_GRAYSCALE)
        return [ leer_imagen(f.path) for f in os.scandir(carpeta) if
            is_image(f.path) ]

    def reescalar_imagen(self, img): # funcion 3
        '''Reescala todas las imagenes dadas a la dimension especificada por la
        variable de instancia'''
        return cv2.resize(img, (self.IMG_SIZE, self.IMG_SIZE))

    def etiqueta(self, imagen, numero): # funcion 4
        """Función que crea una tupla que consiste en una imagen y un número que
        representa una etiqueta.
        Parámetros: 
        -imagen: imagen a la que se le asignará una etiqueta.
        -numero: entero no negativo que corresponde a una etiqueta.
        """
        return (imagen, numero)

    def listaImagenEtiqueta(self, tupla):    #Función número 5.
        """Función que reciba una tupla conformada por una imagen y una etiqueta;
        posteriormente la agrega a una lista.
        Parámetros: 
        -tupla: tupla que será agregada a una lista.
        """
        self.IMAGES.append(tupla)

    def shuffle(self, list): # funcion 6
        '''
        Función 6
        Mezcla aleatoriamente los elementos de una lista.
        @param list    Lista
        @return    Lista mezclada
        '''
        for i in range(len(list)):
            r = random.randint(0, len(list) - 1)
            tmp = list[r]
            list[r] = list[i]
            list[i] = tmp

        return list

    def aplicaNumpy(self, listaTuplas): # funcion 7 y 8
        """Función que recibe una lisa de tuplas (imagen, etiqueta), procesa
        las imagenes con numpy y devuelve una tupla con dos elementos: la lista de
        imágenes y la lista de etiquetas"""
        imagenes = []
        etiquetas = []
        for i in range(0,len(listaTuplas)): 
            imagenes.append(listaTuplas[i][0])
            etiquetas.append(listaTuplas[i][1])
        imagenes = np.array(imagenes).reshape(-1, self.IMG_SIZE, self.IMG_SIZE, 1)
        imagenes = imagenes/255.0
        tuplaImagenesEtiquetas = (imagenes, etiquetas)
        return tuplaImagenesEtiquetas

    def escribirArchivo(self, tuplaImEt): # funcion 9
        """Funcion que recibe una tupla de listas (imagenes,etiquetas),
        escribe la lista  de imagenes a un archivo llamado features.pickle
        y la  de etiquetas a un archivo llamado labels.pickle"""
        archivoI = open('features.pickle', 'wb')
        pickle.dump(tuplaImEt[0], archivoI)
        archivoI.close()
        archivoE = open('labels.pickle', 'wb')
        pickle.dump(tuplaImEt[1], archivoE)
        archivoE.close() 

main = Practica04()
main.main('pruebas', 50)
