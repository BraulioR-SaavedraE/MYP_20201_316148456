import unittest
import pickle
import cv2

class GeneralTest(unittest.TestCase):
    """Pruebas generales para el clasificador"""

    def test_falso_positivo(self):
        """Se tienen 100 imágenes, en las cuales no aparece ninguno
           de los 5 animales, se busca ver cuántas veces el clasificador
           cuenta alguno de estos cinco animales donde no los hay"""
        clasificador = GeneralClassifier()
        falsos = 0
        t = 0
        iterador = Practica04()
        iterable = iterador.iterar_imagenes('./imagenesprueba')
        a = ""
        for im in iterable:
            a = clasificador.predict(cv2.resize(im, (64, 64)))

            if a != 'ninguno':
                falsos += 1
            else:
            	t += 1

        print("Se pasaron 100 imágenes donde ninguna era uno de los 5 animales del clasificador")
        s = "En {} ocasiones el clasificador acertó diciendo no haber reconocido a ningún animal"
        print(s.format(falsos))
        x = "Por otro lado, fueron {} las veces que el clasificador dijo haber reconocido a algún animal"
        print(x.format(t))

        if falsos < ((falsos + t) / 2) - 1:
            self.fail('No se acertó ni siquiera en la mitad de las muestras')
        else:
        	pass

    def test_reconoce_hippo(self):
        """Se dan 100 imágenes donde aparecen hipópotamos. Se busca la cantidad
    	   de veces que el clasificador los detecta"""
        clasificador = GeneralClassifier()
        reconoce = 0
        cuenta = 0
        iterador = Practica04()
        iterable = iterador.iterar_imagenes('./imagenes/train/Hippopotamus')
        a = ""
        for im in iterable:
            cuenta += 1
            a = clasificador.predict(cv2.resize(im, (64, 64)))

            if a == 'hippopotamus':
                reconoce += 1

        print("Se pasaron 100 imágenes donde en todas aparecían hipopótamos")
        s = "En {} ocasiones el clasificador pudo identificar hipopótamos"
        print(s.format(reconoce))
        if reconoce < cuenta / 2:
            self.fail('No se acertó ni siquiera en la mitad de las muestras de hipopótamos')
        else:
            pass

    def test_reconoce_pingu(self):
        """Se dan 100 imágenes donde aparecen pingüinos. Se busca la cantidad
    	   de veces que el clasificador los detecta"""
        clasificador = GeneralClassifier()
        reconoce = 0
        cuenta = 0
        iterador = Practica04()
        iterable = iterador.iterar_imagenes('./imagenes/train/Penguin')
        a = ""
        for im in iterable:
            cuenta += 1
            a = clasificador.predict(cv2.resize(im, (64, 64)))

            if a == 'penguin':
                reconoce += 1

        print("Se pasaron 100 imágenes donde en todas aparecían pingüinos")
        s = "En {} ocasiones el clasificador pudo identificar pingüinos"
        print(s.format(reconoce))

        if reconoce < cuenta / 2:
            self.fail('No se acertó ni siquiera en la mitad de las muestras de pingüinos')
        else:
            pass

    def test_reconoce_avestruz(self):
        """Se dan 100 imágenes donde aparecen avestruces. Se busca la cantidad
    	   de veces que el clasificador las detecta"""
        clasificador = GeneralClassifier()
        reconoce = 0
        cuenta = 0
        iterador = Practica04()
        iterable = iterador.iterar_imagenes('./imagenes/train/Ostrich')
        a = ""
        for im in iterable:
            cuenta += 1
            a = clasificador.predict(cv2.resize(im, (64, 64)))

            if a == 'ostrich':
                reconoce += 1

        print("Se pasaron 100 imágenes donde en todas aparecían avestruces")
        s = "En {} ocasiones el clasificador pudo identificar avestruces"
        print(s.format(reconoce))

        if reconoce < cuenta / 2:
            self.fail('No se acertó ni siquiera en la mitad de las muestras de avestruces')
        else:
            pass

    def test_reconoce_cuyo(self):
        """Se dan 100 imágenes donde aparecen cuyos. Se busca la cantidad
    	   de veces que el clasificador los detecta"""
        clasificador = GeneralClassifier()
        reconoce = 0
        cuenta = 0
        iterador = Practica04()
        iterable = iterador.iterar_imagenes('./imagenes/train/Hamster')
        a = ""
        for im in iterable:
            cuenta += 1
            a = clasificador.predict(cv2.resize(im, (64, 64)))

            if a == 'hamster':
                reconoce += 1

        print("Se pasaron 100 imágenes donde en todas aparecían cuyos")
        s = "En {} ocasiones el clasificador pudo identificar cuyos"
        print(s.format(reconoce))

        if reconoce < cuenta / 2:
            self.fail('No se acertó ni siquiera en la mitad de las muestras de cuyos')
        else:
            pass

    def test_reconoce_jirafa(self):
        """Se dan 100 imágenes donde aparecen jirafas. Se busca la cantidad
    	   de veces que el clasificador las detecta"""
        clasificador = GeneralClassifier()
        reconoce = 0
        cuenta = 0
        iterador = Practica04()
        iterable = iterador.iterar_imagenes('./imagenes/train/Giraffe')
        a = ""
        for im in iterable:
            cuenta += 1
            a = clasificador.predict(cv2.resize(im, (64, 64)))

            if a == 'Giraffes':
                reconoce += 1

        print("Se pasaron 100 imágenes donde en todas aparecían jirafas")
        s = "En {} ocasiones el clasificador pudo identificar jirafas"
        print(s.format(reconoce))

        if reconoce < cuenta / 2:
            self.fail('No se acertó ni siquiera en la mitad de las muestras de jirafas')
        else:
            pass

#función que hace correr la clase
if __name__ == '__main__':
    import os
    import sys
    sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

    from GeneralClassifier import GeneralClassifier
    from practica04 import Practica04
    unittest.main()