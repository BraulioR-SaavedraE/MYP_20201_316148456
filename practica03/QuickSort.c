#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int* quickSort(int* arreglo, int inicio, int fin);
void intercambia(int* a, int* b);
int* quickSortParallel(int* arreglo, int tamano);

/*	El método main lee enteros de la entrada estándar y hace que se desplieguen
 *	n números ordenados, donde n es el primer entero de la entrada.	*/
int main()
{
    int numero, k;
    int i = 0;
    scanf("%d", &numero);
    int arreglo[numero];

    while (i < numero){
        scanf("%d", &k);
        arreglo[i++] = k;
    }

    int* y = quickSortParallel(arreglo, numero);
    for(int x = 0; x < numero; x++)
    	printf("%d ", y[x]);

    printf("\n");
}

/* Método de ordenamiento de enteros.*/
int* quickSort(int* arreglo, int inicio, int fin) 
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

/*	Método auxiliar que intercambia los elementos de dos localidades en un arreglo.	*/
void intercambia(int* a, int* b)
{
        int elementoA = *a;
        *a = *b;
        *b = elementoA;
}

/*	Método que mezcla dos subarreglos ya ordenados y produce uno también ordenado.	*/
int* mezcla(int* arreglo, int indices[][2], int longitud)
{
        int *new = (int *) malloc(longitud* sizeof(int));
        int elementos = 0;
        int i = indices[0][0];
        int j = indices[1][0];

        while (i <= (indices[0][1]) && j <= indices[1][1])

            if (arreglo[i] <= arreglo[j])
                new[elementos++] = arreglo[i++];
            else
                new[elementos++] = arreglo[j++];
            
        if (i > indices[0][1]) 
            while (j <= indices[1][1])
            	new[elementos++] = arreglo[j++];
        else
            while (i <= indices[0][1])
            	new[elementos++] = arreglo[i++];

            
        arreglo = &new[0];

        return arreglo;
}

/*	Versión paralela del algoritmo QuickSort.	*/
int* quickSortParallel(int* arreglo, int longitud)
{
	int medio = (longitud - 1) /2;
	int i;
  
  	omp_set_num_threads(2);
  	int indices[2][2]; 
  	indices[0][0] = 0;
  	indices[0][1] = medio;
  	indices[1][0] = medio + 1;
  	indices[1][1] = longitud - 1;

  	#pragma omp parallel
  {
  	#pragma omp for
    	for(i = 0; i < 2; i++)
    		quickSort(arreglo, indices[i][0] , indices[i][1]);
  }

	#pragma opm barrier
	int* ok = &indices[0][0];

    arreglo = mezcla(arreglo, indices, longitud);

    return arreglo;
}