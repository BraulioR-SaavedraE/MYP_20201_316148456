import unittest
import pickle

def load_pickles(fpath='./pickles/jirafatesteada.pickle',
        lpath='./pickles/jirafatesteadalabels.pickle'):
    with open(fpath, 'rb') as f:
        features = pickle.load(f)
    with open(lpath, 'rb') as f:
        labels = pickle.load(f)
    return features, labels


class GiraffesClassifierTest(unittest.TestCase):
    """Pruebas para GiraffesClassifier."""
    
    def testConstructor(self):
        """Constructor de la clase."""
        print('Constructor test...')
        clasificador = GiraffesClassifier()

   

    def test_fit(self):
         """Entrena el modelo de jirafas.
         excepciones : ValueError, si el clasificador acepta valores nulos
         clasificador acepta arreglos numpy de labels y features de tamaños diferentes
         """
         print('Fit test...')
         inicializador = Practica04()
         inicializador.main('./imagenes/train', 64)

         features, labels = load_pickles()
         clasificador = GiraffesClassifier()
         
         try:
             clasificador.fit(None, None)
             unittest.fail('el clasificador acepta parametros nulos')
         except ValueError:
             pass
         
         try:
             clasificador.fit(features, labels[:-1])
             unittest.fail('el clasificador acepta features y labels de ' +
                           'tamano diferente')
         except ValueError:
             pass
         
         clasificador.fit(features, labels)
         assert clasificador.model is not None
         
    def test_evaluate(self):
        """Evalua el modelo.
        excepciones: ValueError, si el clasificador acepta parametros nulos
        clasificador no es preciso (accuracy < 0.7)
        """
        print('Evaluate test...')
        inicializador = Practica04()
        inicializador.main('./imagenes/train', 64)
        features, labels = load_pickles()
        clasificador = GiraffesClassifier()
        clasificador.fit(features, labels)

        inicializador.main('./imagenes/test', 64)
        features, labels = load_pickles()

        try:
            clasificador.evaluate(None, None)
            unittest.fail('el clasificador acepta parametros nulos')
        except ValueError:
            pass

        try:
            clasificador.evaluate(features, labels[:-1])
            unittest.fail('el clasificador acepta features y labels de ' +
                    'tamano diferente')
        except ValueError:
            pass

        ev = clasificador.evaluate(features, labels)
        if ev['acc'] < 0.7:
            unittest.fail('el clasificador no es suficientemente preciso')
"""Funcion principal de la clase"""
if __name__ == '__main__':
    import os
    import sys
    sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

    from GiraffesClassifier import GiraffesClassifier
    from practica04 import Practica04
unittest.main()
