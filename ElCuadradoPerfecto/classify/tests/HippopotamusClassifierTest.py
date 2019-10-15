import unittest
import pickle

class HippopotamusClassifierTest(unittest.TestCase):
    """ Pruebas unitarias para la clase Hipopotamus """

    def testConstructor(self):
        """ Prueba para el constructor """
        clasificador = HippopotamusClassifier()

    def test_load(self):
        """ Prueba al iniciar el clasificador, debe
        cargar el modelo con los pesos guardados
        en checkpoints/hippopotamus"""
        clasificador = HippopotamusClassifier()
        assert clasificador.model is not None

    def test_evaluate(self):
        """ Prueba para evaluar la punteria del modelo, se
        requiere un conjunto de imagenes normalizadas en un archivo
        llamado test_images.pickle """
        clasificador = HippopotamusClassifier()
        with (open("./pickles/hippopotamus_images.pickle", "rb")) as f:
            features = pickle.load(f)
        with (open("./pickles/hippopotamus_labels.pickle", "rb")) as f:
            labels = pickle.load(f)
        try:
            clasificador.evaluate(None, None)
            unittest.fail("el clasificador acepta parametros nulos")
        except ValueError:
            pass

        try:
            clasificador.evaluate(features, labels[:-1])
            unittest.fail("el clasificador acepta features y labels de " +
                    "tamano diferente")
        except ValueError:
            pass

        ev = clasificador.evaluate(features, labels)
        if ev["acc"] < 0.7:
            unittest.fail("el clasificador no es suficientemente preciso")

if __name__ == '__main__':
    import os
    import sys
    sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

    from HippopotamusClassifier import HippopotamusClassifier
    unittest.main()
