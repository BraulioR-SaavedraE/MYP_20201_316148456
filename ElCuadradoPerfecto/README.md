# Practica04
Algoritmo que lee imagenes en una subcarpeta del proyecto y las procesa.

## Prerrequisitos
1. PIP
```
sudo apt-get install python-pip
sudo apt-get install python3-pip
```
2. VirtualEnv
```
pip install virtualenv
sudo pip3 install virtualenv
```
## Instalación
1. Iniciar ambiente virtual
```
virtualenv env
source env/bin/activate
```
2. Instalar librerias desde PIP
```
pip install -r librerias.txt
```
3. Descargar imágenes usando google_images_download
```
mkdir imagenes
googleimagesdownload -cf imagenes.json -o imagenes
```
3. Ejecutar el archivo principal
```
python3 practica04.py
```