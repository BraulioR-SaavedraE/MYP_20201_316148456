import unittest
import pickle

def load_pickles(fpath='./pickles/pinguinoentrenado.pickle',
        lpath='./pickles/pinguinoentrenadolabels.pickle'):

    with open(fpath, 'rb') as f:
        features = pickle.load(f)
    with open(lpath, 'rb') as f:
        labels = pickle.load(f)
    return features, labels

class PenguinClassifierTest(unittest.TestCase):
    def testConstructor(self):
        print('Constructor test...')
        clasificador = PenguinClassifier()

    def test_fit(self):
        print('Fit test...')
        inicializador = Practica04()
        inicializador.main('./imagenes/train', 64)

        features, labels = load_pickles()
        clasificador = PenguinClassifier()

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
        print('Evaluate test...')
        inicializador = Practica04()
        inicializador.main('./imagenes/train', 64)
        features, labels = load_pickles()
        clasificador = PenguinClassifier()
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

if __name__ == '__main__':
    import os
    import sys
    sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

    from PenguinClassifier import PenguinClassifier
    from practica04 import Practica04
    unittest.main()
