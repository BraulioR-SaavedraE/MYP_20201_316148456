import numpy as np
import keras
from keras.models import Sequential
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
from keras.optimizers import SGD
import pickle

class AnimalClassifier:
    '''
    Clase general para clasificar algun animal de un dataset especifico. Esta
    pensado para que sus subclases especifiquen las capas del modelo que mas
    convenga para determinar el animal y la etiqueta del animal que
    representa.'''
    # label: el label de la imagene que quiere clasificar. por ejemplo, si
    # quiere clasificar puros cuyos y el label de cuyo=0, marcara a todos los
    # samples con etiqueta 0 como ejemplar positivo y la los demas (jirafa, etc)
    # como negativo.
    # layers: las capas que se le pasara a la red neuronal al momento de
    # entrenar
    def __init__(self, label, layers, loss='poisson', optimizer='adam'):
        '''
        Construye un clasificador de animal con las especificaciones dadas. Para
        mas informacion sobre las capas, la funcion de perdida y algoritmo de
        optimizacion disponibles, consulte la documentacion de Keras.
        Parametros:
        - label: La etiqueta que representa al animal que se quiere clasificar.
        - layers: Una lista con las capas de la red neuronal a ocupar.
        - loss: La funcion de perdida que se desea optimizar en la red neuronal.
        - optimizer: El algoritmo de optimizacion para la red neuronal.'''

        # checa parametros correctos
        if type(label) is not int or label < 0:
            raise ValueError('La etiqueta de label debe se ser un numero ' +
                    'mayor o igual a cero')

        try: iter(layers)
        except TypeError:
            raise ValueError('El parametro layers debe de ser iterable')

        if not len(layers):
            raise ValueError('El parametro layers no debe ser vacio')

        if not all([ isinstance(layer, keras.layers.Layer) for layer in layers ]):
           raise ValueError('El parametro layers debe ser lista de capas de ' +
                   'Keras')

        try: keras.losses.get(loss)
        except ValueError:
            raise ValueError('La funcion loss de perdida dada es invalida')

        try: keras.optimizers.get(optimizer)
        except ValueError:
            raise ValueError('El algoritmo de optimizacion dado es invalido')


        # inicio de constructor
        self.label = label
        self.layers = layers
        self.loss = loss
        self.optimizer = optimizer
        self.model = Sequential(self.layers)

        self.model = Sequential(self.layers)
        self.model.compile(loss=self.loss, optimizer=self.optimizer,
                metrics=['accuracy'])

    def validar_xy(self, x, y):
        '''
        Funcion de uso interno. Determina si los parametros cumplen
        requisitos de ser conjunto de datos de muestra y de etiquetas.
        Parametros:
        - x: Muestras.
        - y: Etiquetas.
        '''

        if not isinstance(x, np.ndarray):
            raise ValueError('Parametro x debe ser ndarray')

        try: len(y)
        except TypeError:
            raise ValueError('Parametros y debe ser iterable')

        if not len(x) or not len(y):
            raise ValueError('Parametros no deben ser vacios')

        if len(x) != len(y):
            raise ValueError('Debe de haber misma cantidad de muestras y de ' +
                    'etiquetas')


    def fit(self, x, y, val_data=None):
        '''
        Entrenamiento principal del modelo. Utiliza la etiqueta pasada en el
        constructor para normalizar las etiquetas en el parametro y dado: toma
        como muestra positiva a cualquiera que tenga la misma etiqueta que la
        especificada anteroiormente y como negativa a cualquier otra.

        Entrena la red neuronal interna con las muestras y etiquetas pasadas
        como parametro, si es especificado la data de validacion, se utiliza
        tambien al entrenar.

        Parametros:
        - x: numpy.ndarray con las muestras con las que se va a entrenar.
        - y: iterable con las etiquetas de las muestras
        - val_data: None si no se desea utilizar o una tupla (x_val, y_val) con
          las muestras y etiquetas de la validacion interna del modelo
        '''

        # parametros correctos
        self.validar_xy(x, y)
        if val_data is not None:
            if len(val_data) != 2:
                raise ValueError('Validation data invalido')
            self.validar_xy(*val_data)

        # inicio de fit
        # actualizado de etiquetas. 1 para la que buscamos, cero las demas
        y = [ 1 if label == self.label else 0 for label in y ]
        if val_data is not None:
            new_y = [ 1 if label == self.label else 0 for label in
                    val_data[1] ]
            val_data = (val_data[0], new_y)
        
        self.model.fit(x, y, batch_size=32, epochs=10, validation_data=val_data)

    def evaluate(self, x, y):
        '''
        Evalua el modelo entrenado anteriormente con el conjunto de datos
        especificado.
        Parametros:
        - x: Muestras.
        - y: Etiquetas.
        '''

        # parametros correctos
        self.validar_xy(x, y)
        if self.model is None:
            raise ValueError('Para poder evaluar el modelo se debio haber ' +
                    'entrenado anteriormente')

        # evaluate
        y = [ 1 if label == self.label else 0 for label in y ]
        return dict(zip(self.model.metrics_names, self.model.evaluate(x, y)))

    def save(self, name):
        self.model.save_weights('./checkpoints/' + name)

    def load(self, name):
        self.model.load_weights('./checkpoints/' + name)

    def predict(self, test):
        return self.model.predict(test)
