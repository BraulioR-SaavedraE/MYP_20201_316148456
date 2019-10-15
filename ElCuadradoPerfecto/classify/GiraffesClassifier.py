from AnimalClassifier import AnimalClassifier
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
import tensorflow as tf
import pickle

class GiraffesClassifier(AnimalClassifier):
    """Constructor de la clase.
    Crea una instancia de AnimalClassifer al que manda de parametros:
    capas -- capas que usara el modelo para entrenarse
    label -- etiqueta especifica de la imagen a clasificar en este caso es 5 (jirafa)
    """
    def __init__(self):
        capas = [
                Conv2D(32, (3, 3), activation='relu', input_shape=(64, 64, 1)),
                MaxPooling2D(pool_size=(2, 2)),
                Flatten(),
                Dense(1, activation='sigmoid')      
            
        ]

        label = 5
        optimizer = 'adam'
        loss = 'poisson'
        super().__init__(label, capas, loss, optimizer)
        
        """Main de la clase"""
if __name__ == '__main__':

    'Archivos .pickle para entrenar el modelo'
    with open('./jirafaentrenada.pickle', 'rb') as f:
        x_train = pickle.load(f)
    with open('./jirafaentrenadalabels.pickle', 'rb') as f:
        y_train = pickle.load(f)

    classifier = GiraffesClassifier()
    'Entrenar el modelo'
    classifier.fit(x_train, y_train)

    'Archivos .pickle para probar el modelo'
    with open('./jirafatesteada.pickle', 'rb') as f:
        x_test = pickle.load(f)
    with open('./jirafatesteadalabels.pickle', 'rb') as f:
        y_test = pickle.load(f)

    print('final:')
    'Evaluar el modelo'
    print(classifier.evaluate(x_test, y_test))
    'Imprimir en terminal aciertos y fallos de las pruebas'
    classifier.save('Giraffes')
