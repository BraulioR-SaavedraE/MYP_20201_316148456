import argparse
import urllib

import cv2

from GeneralClassifier import GeneralClassifier

def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('--mode', default='link', choices=['link', 'local'],
            help='Determina como va a buscar la imagen. link=va a descargar ' +
            'la imagen del link dado. local=busca localmente y carga la ' +
            'imagen especificada')
    parser.add_argument('imagenes', nargs='+', type=str, help='Las imagenes ' +
            'que se desean predecir')
    return parser.parse_args()

def leer_imagen(path):
    return cv2.imread(path, cv2.IMREAD_GRAYSCALE)

def descargar_imagen(link):
    urllib.request.urlretrieve(link, './.temp.jpg')
    return leer_imagen('./.temp.jpg')

def leer_imagen_original(path):
    return cv2.imread(path)

def descargar_imagen_original(link):
    urllib.request.urlretrieve(link, './.temp.jpg')
    return leer_imagen_original('./.temp.jpg')

def main(args):
    if args.mode == 'local':
        leer = leer_imagen
        leer_original = leer_imagen_original

    elif args.mode == 'link':
        leer = descargar_imagen
        leer_original = descargar_imagen_original

    imgs = [ (leer(img), leer_original(img)) for img in args.imagenes ]
    imgs = [ (cv2.resize(img, (64, 64)), orig) for img, orig in imgs ]

    clasificador = GeneralClassifier()
    for img, original in imgs:
        predict = clasificador.predict(img)
        cv2.imshow('predict: %s' % predict, original)
        cv2.waitKey(0)

if __name__ == '__main__':
    main(parse_args())
