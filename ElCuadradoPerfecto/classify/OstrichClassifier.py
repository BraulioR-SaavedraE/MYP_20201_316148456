from AnimalClassifier import AnimalClassifier
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
import pickle

class OstrichClassifier(AnimalClassifier):
    '''
    Clasificador de avestruz
    '''

    def __init__(self):
        '''
        Inicializa el clasificador
        '''
        capas = [
                Conv2D(32, (3, 3), activation='relu', input_shape=(64, 64, 1)),
                MaxPooling2D(pool_size=(2, 2)),
                Flatten(),
                Dense(1, activation='sigmoid')
        ]

        label = 3
        optimizer = 'adam'
        loss = 'binary_crossentropy'
        super().__init__(label, capas, loss, optimizer)

if __name__ == '__main__':
    with open('./x_train.pickle', 'rb') as f:
        x_train = pickle.load(f)
    with open('./y_train.pickle', 'rb') as f:
        y_train = pickle.load(f)

    with open('./x_val.pickle', 'rb') as f:
        x_val = pickle.load(f)
    with open('./y_val.pickle', 'rb') as f:
        y_val = pickle.load(f)

    classifier = OstrichClassifier()
    print(len(x_train))
    print(len(x_val))
    classifier.fit(x_train, y_train, (x_val, y_val))


    with open('./x_test.pickle', 'rb') as f:
        x_test = pickle.load(f)
    with open('./y_test.pickle', 'rb') as f:
        y_test = pickle.load(f)

    print('final:')
    print(classifier.evaluate(x_test, y_test))

    classifier.save('ostrich')
