#include <stdio.h>
#include <stdlib.h>

int* quickSort(int arreglo[], int a, int b);
void intercambia(int* a, int* b);

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

    int* y = quickSort(arreglo, 0, numero -1);
    for(; i < numero; i++)
        printf("%d ", arreglo[i]);
}

/* Método de ordenamiento de enteros.*/
int* quickSort(int* arreglo, int a, int b) 
{
        if(b <= a)
            return 0;

        int i = a + 1;
        int j = b;

        while (i < j){

            if(arreglo[i] > arreglo[a] && arreglo[j] <= arreglo[a]){
                intercambia(&arreglo[i], &arreglo[j]);
                i += 1;
                j -= 1;
            }else if(arreglo[i] <= arreglo[a]){
                i += 1;
            }else
                j -= 1;
        }

        if(arreglo[i] > arreglo[a])
            i -= 1;

        intercambia(&arreglo[a], &arreglo[i]);
        quickSort(arreglo ,a, i - 1);
        quickSort(arreglo, i + 1, b);
}

/* Método auxiliar que intercambia los elementos de dos localidades en un arreglo. */
void intercambia(int* a, int* b)
{
        int elementoA = *a;
        *a = *b;
        *b = elementoA;
}