from HippopotamusClassifier import HippopotamusClassifier
from OstrichClassifier import OstrichClassifier
from PenguinClassifier import PenguinClassifier
from HamsterClassifier import HamsterClassifier
from GiraffesClassifier import GiraffesClassifier

import numpy as np

class GeneralClassifier:
    '''
    Une los cinco clasificadores de animales para crear un modelo que pueda
    diferenciar entre estos cinco animales. Para instanciarse, se requiere que
    se haya entrenado cada uno de estos cinco modelos y haber gurdados los
    modelos ya entrenados con anterioridad.
    '''
    def __init__(self):
        self.classifiers = {
                'hippopotamus': HippopotamusClassifier,
                'ostrich': OstrichClassifier,
                'penguin': PenguinClassifier,
                'hamster': HamsterClassifier,
                'Giraffes': GiraffesClassifier
        }

        for key, model in self.classifiers.items():
            self.classifiers[key] = model()
            self.classifiers[key].load(key)

    def predict(self, sample):
        '''
        Realiza una prediccion de una imagen. Esta va a ser pasada por cada uno
        de los modelos que esta clase integra y determinará si es alguno de
        estos animales. Si todos los modelos responden negativamente, regresará
        que no es ninguna de estos animales.

        Parametros:
        ----
        - sample: la imagen que se quiere predecir
        '''

        sample = np.array([ sample ]).reshape(1, 64, 64, 1)
        for name, model in self.classifiers.items():
            a = model.predict(sample)
            if a[0] == 1:
                return name

        return 'ninguno'
