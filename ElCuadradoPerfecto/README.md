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
3. Banco de imágenes
```
https://www.dropbox.com/sh/x7gms2kon2hrlg6/AADxfPOA0WkCXeeS99K-dlBda?dl=0
https://drive.google.com/open?id=1uhHC6eIgT2PaO4Fl0tkjcmHnP6TMNggA
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