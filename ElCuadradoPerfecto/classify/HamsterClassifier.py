from AnimalClassifier import AnimalClassifier
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
import pickle

class HamsterClassifier(AnimalClassifier):
    ''' Clasificador de cuyos '''

    def __init__(self):
        ''' método constructor '''
        capas = [
                Conv2D(32, (3, 3), activation='relu', input_shape=(64, 64, 1)),
                MaxPooling2D(pool_size=(2, 2)),
                Flatten(),
                Dense(1, activation='sigmoid')
        ]

        label = 1
        optimizer = 'adam'
        loss = 'poisson'
        super().__init__(label, capas, loss, optimizer)
        try:
            self.load('hamster')
        except Exception as e:
            print("Warning: HamsterClassifier no pudo cargar el modelo guardado.")
            print(e)

if __name__ == '__main__':
    ''' Código para generar el modelo y entrenarlo
    solo es necesario para crearlo, una vez guardado puede
    ignorarse'''

    with open('./train_hamster_images.pickle', 'rb') as f:
        x_train = pickle.load(f)
    with open('./train_hamster_labels.pickle', 'rb') as f:
        y_train = pickle.load(f)

    print(len(x_train))
    classifier = HamsterClassifier()
    classifier.fit(x_train, y_train)

    with open('./hamster_images.pickle', 'rb') as f:
        x_test = pickle.load(f)
    with open('./hamster_labels.pickle', 'rb') as f:
        y_test = pickle.load(f)
    
    print('final:')
    print(classifier.evaluate(x_test, y_test))
    classifier.save('hamster')

    predictions = classifier.predict(x_test)
    print(predictions[0])