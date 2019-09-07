#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int quickSort(int* arreglo, int inicio, int fin);
void intercambia(int* a, int* b);
void quickSortParallel(int* arreglo, int tamano);

/* El método main lee enteros de la entrada estándar y hace que se desplieguen
 * n números ordenados, donde n es el primer entero de la entrada-
 */
int main()
{
    int numero, k;
    int i = 0;
    scanf("%d", &numero);
    int arreglo[numero];

    while ((i) < numero){
        scanf("%d", &k); 
        arreglo[i++] = k;
    }
    i = 0;

    quickSortParallel(arreglo, numero);

    for(; i < numero; i++)
        printf("%d ", arreglo[i]);
}

/* Método de ordenamiento de enteros.*/
int quickSort(int* arreglo, int inicio, int fin) 
{
        if(fin <= inicio)
            return 0;

        int i = inicio + 1;
        int j = fin;

        while (i < j){

            if(arreglo[i] > arreglo[inicio] && arreglo[j] <= arreglo[inicio]){
                intercambia(&arreglo[i], &arreglo[j]);
                i += 1;
                j -= 1;
            }else if(arreglo[i] <= arreglo[inicio]){
                i += 1;
            }else
                j -= 1;
        }

        if(arreglo[i] > arreglo[inicio])
            i -= 1;

        intercambia(&arreglo[inicio], &arreglo[i]);
        quickSort(arreglo ,inicio, i - 1);
        quickSort(arreglo, i + 1, fin);
}

/* Método auxiliar que intercambia los elementos de dos localidades en un arreglo. */
void intercambia(int* a, int* b)
{
        int elementoA = *a;
        *a = *b;
        *b = elementoA;
}

int* mezcla(int* arreglo, int** indices)
{
        int arregloNuevo[20];
        int elementos = 0;
        int i = indices[0][0];
        int j = indices[1][0];

        while (i < (indices[0][1]) && j < indices[1][1]) 
            if (arreglo[i] <= arreglo[j]){
            	printf("%s","sdsa");
                arregloNuevo[elementos++] = arreglo[i++];
            }else{
                arregloNuevo[elementos++] = arreglo[j++];
            }

        if (i == indices[0][1]) 
            while (j < indices[1][1]){
            	arregloNuevo[elementos++] = arreglo[j++];
            }
        else{
            while (i < indices[0][1]) {
            	arregloNuevo[elementos++] = arreglo[i++];
            }
        }

        return arregloNuevo;
}

void quickSortParallel(int* arreglo, int longi)
{
 int middle = (longi - 1) /2;
  int i;
  
  omp_set_num_threads(2);
  int** t;
  int indices[2][2]; 
  indices[0][0] = 0;
  indices[0][1] = middle;
  indices[1][0] = middle +1;
  indices[1][1] = longi-1;
  #pragma omp parallel
  {
    #pragma omp for
    for(i = 0; i < 2; i++){
      quickSort(arreglo, indices[i][0] , indices[i][1]);
    }
  }

  #pragma opm barrier
  quickSort(arreglo, 0, longi -1);
  //int* gron = &indices[0][0];
  //mezcla(arreglo, &gron);
}