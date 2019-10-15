import unittest
import pickle

def load_pickles(fpath='./pickles/ostrich_features.pickle',
        lpath='./pickles/ostrich_labels.pickle'):

    with open(fpath, 'rb') as f:
        features = pickle.load(f)
    with open(lpath, 'rb') as f:
        labels = pickle.load(f)
    return features, labels

class OstrichClassifierTest(unittest.TestCase):
    '''
    Pruebas unitarias para la clase de clasificasion de Avesturz
    '''

    def testConstructor(self):
        '''
        Prueba para el constructor
        '''

        print('Constructor test...')
        clasificador = OstrichClassifier()

    def test_fit(self):
        '''
        Prueba para entrenar el clasificador. Crea unos vectores aleatorios e
        intenta entrenar al clasificador con estos
        '''

        import numpy as np
        from random import randrange as rand
        print('Fit test...')

        n = rand(32, 64)
        features = np.array([[1,1,1], [1,1,1], [1,1,1], [1,1,1]])
        features = np.random.rand(n, 64, 64, 1)
        labels = [ rand(0, 10) for _ in range(n) ]
        clasificador = OstrichClassifier()

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
        '''
        Prueba para evaluar la presicion del modelo, se requiere un conjunto de
        imagenes normalizadas de los archivos llamados
        ./pickles/ostrich_features.pickle y ./pickles/ostrich_labels.pickle
        '''

        print('Evaluate test...')
        features, labels = load_pickles()
        clasificador = OstrichClassifier()
        clasificador.load('ostrich')
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

    from OstrichClassifier import OstrichClassifier
    unittest.main()
