from AnimalClassifier import AnimalClassifier
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
import pickle

class PenguinClassifier(AnimalClassifier):
    def __init__(self):
        capas = [
                Conv2D(32, (3, 3), activation='relu', input_shape=(64, 64, 1)),
                MaxPooling2D(pool_size=(2, 2)),
                Flatten(),
                Dense(1, activation='sigmoid')
        ]

        label = 2
        optimizer = 'adam'
        loss = 'poisson'
        super().__init__(label, capas, loss, optimizer)

if __name__ == '__main__':
    with open('./pinguinoentrenado.pickle', 'rb') as f:
        x_train = pickle.load(f)
    with open('./pinguinoentrenadolabels.pickle', 'rb') as f:
        y_train = pickle.load(f)

    classifier = PenguinClassifier()
    classifier.fit(x_train, y_train)

    with open('./pinguinotesteado.pickle', 'rb') as f:
        x_test = pickle.load(f)
    with open('./pinguinotesteadolabels.pickle', 'rb') as f:
        y_test = pickle.load(f)

    print('final:')
    print(classifier.evaluate(x_test, y_test))
    classifier.save('Penguin')