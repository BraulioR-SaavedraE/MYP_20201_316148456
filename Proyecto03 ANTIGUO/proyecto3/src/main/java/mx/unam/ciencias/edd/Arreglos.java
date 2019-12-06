package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        quickSort(arreglo, comparador, 0, arreglo.length - 1);
    }

    /*
     * Método auxiliar para aplicar quickSort de manera recursiva
     * @param arreglo el arreglo a ordenar
     * @param comparador comparador encargado de comparar los elementos del arreglo
     * @param ini índice izquierdo del arreglo 
     * @param fin índice derecho del arreglo 
     */
    private static <T> void quickSort(T [] arreglo, Comparator <T> comparador, int ini, int fin){
        if(fin <= ini) 
            return;
        int i = ini + 1; 
        int j = fin;
        while (i < j)
            if (comparador.compare(arreglo[i], arreglo[ini]) > 0 && comparador.compare(arreglo[j], arreglo[ini]) <= 0)
                intercambia(arreglo, i++, j--);
            else if (comparador.compare(arreglo[i], arreglo[ini]) <= 0)
                i++;
            else
                j--;
        if(comparador.compare(arreglo[i], arreglo[ini]) > 0)
            i--;
        intercambia(arreglo, i, ini);
        quickSort(arreglo, comparador, ini, i - 1);
        quickSort(arreglo, comparador, i + 1, fin);
    }


    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /*
     * Método auxiliar que intercambia los elementos dados en dos índices en un arreglo
     * @param arreglo arreglo donde se hará el intercambio
     * @param indice1 índice de uno de los elementos a intercambiar
     * @param indice2 el otro índice de uno de los elementos a intercambiar
     */
    private static <T> void intercambia(T [] arreglo, int indice1, int indice2){
        T aux = arreglo[indice1];
        arreglo[indice1] = arreglo[indice2];
        arreglo[indice2] = aux;
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        if(arreglo.length <= 1)
            return;

        int minimo;
        for(int i = 0; i < arreglo.length -1; i++){
            minimo = i;
            for(int j = i + 1; j < arreglo.length; j++){
                if(comparador.compare(arreglo[j], arreglo[minimo]) < 0){
                    minimo = j;
                }
            }
            intercambia(arreglo, i, minimo);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        if(arreglo.length == 0)
            return -1;

        return busquedaBinaria(arreglo, elemento, comparador, 0, arreglo.length - 1);
    }

    /*
     * Método que hace búsqueda binaria de manera recursiva 
     * @param arreglo arreglo en donde se buscará el elemento
     * @param elemento elemento a buscar
     * @param comparador comparador que se encargará de comparar el elemento del
     * parámetro formal y los que se encuentren en el arreglo
     * @param a extremo izquierdo del subarreglo en el que se buscará
     * @param b extremo derecho del subarreglo en el que se buscará
     * @return el índice del elemento en el arreglo, o -1 si éste no se encuentra.
     */
    private static <T> int busquedaBinaria(T [] arreglo, T elemento, Comparator <T> comparador, int a, int b){
        if(a == b){
            if(comparador.compare(arreglo[a], elemento) == 0)
                return a;
            return -1;
        }
        int medion = a + (b - a) / 2;
        if(comparador.compare(elemento, arreglo[medion]) <=  0)
            return busquedaBinaria(arreglo, elemento, comparador, a, medion);

        return busquedaBinaria(arreglo, elemento, comparador, medion + 1, b);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
