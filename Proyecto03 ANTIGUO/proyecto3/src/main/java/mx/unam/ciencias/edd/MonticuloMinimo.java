package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return (indice < getElementos()); //verificar
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if(indice >= getElementos()) //verificar
                throw new NoSuchElementException();
            
            return(arbol[indice++]);
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return this.elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100); /* 100 es arbitrario. */
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        arbol = nuevoArreglo(n);
        int auxiliar = 0;
        for(T elem : iterable){
            arbol[elementos] = elem;
            elem.setIndice(elementos++); 
        }
        for(int i = (n / 2) -1; i >= 0; i--){
            acomodaAbajo(i);
        }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elementos == arbol.length){
            T [] doble = nuevoArreglo(2 * getElementos());
            for(int i = 0; i < getElementos(); i++){
                doble[i] = arbol[i];
            }
            arbol = doble;
        }
        elemento.setIndice(elementos);
        arbol[elementos++] = elemento;
        acomodaArriba(getElementos() -1);
    }

    /*
     * Método auxiliar que acomoda hacia arriba el montículo mínimo dado un índice
     * @param indice -índice desde el cual se empezará el acomodo
     */
    private void acomodaArriba(int indice){
        if(indice <= 0)
            return;

        int auxiliar = indice;
        if(queHijo(auxiliar)){
            if(arbol[auxiliar].compareTo(arbol[(auxiliar -2) /2]) < 0){
            intercambia(auxiliar, (auxiliar -2) / 2);
            auxiliar = (auxiliar -2) / 2;
            acomodaArriba(auxiliar);
            }else{
                return;
            }
        }else if(!queHijo(auxiliar)){
            if(arbol[auxiliar].compareTo(arbol[(auxiliar -1) / 2]) < 0){
            intercambia(auxiliar, (auxiliar -1) / 2);
            auxiliar = (indice -1) / 2;
            acomodaArriba(auxiliar);
            }else{
                return;
            }
        } 
    }

    /*
     * Método auxiliar que intercambia dos elementos en un arreglo
     * @param posicion1 -índice de uno de los elementos a intercambiar
     * @param posicion2 -índice del otro de los elementos a intercambiar
     */
    private void intercambia(int posicion1, int posicion2){
        arbol[posicion1].setIndice(posicion2);
        arbol[posicion2].setIndice(posicion1);
        T auxiliar = arbol[posicion1];
        arbol[posicion1] = arbol[posicion2];
        arbol[posicion2] = auxiliar;
    }

    /* Método auxiliar que determina si un índice en el arreglo
     * corresponde a ser hijo izquierdo o derecho
     * @return boolean -true en caso de ser derecho, false en caso de ser izquierdo
     */
    private boolean queHijo(int indice){
        if(indice % 2 == 0)
            return true;

        return false;
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
        if(esVacia())
            throw new IllegalStateException();

        T eliminado = arbol[0];
        eliminado.setIndice(0);
        elimina(eliminado);
        eliminado.setIndice(-1);
        return eliminado;
    }

    /*
     * Método auxiliar que acomoda hacia arriba el montículo mínimo dado un índice
     * @param indice -índice desde el cual se empezará el acomodo
     */
    private void acomodaAbajo(int indice){
        if(indice < 0)
            return;
        if((2 * indice) +1 >= getElementos())
            return;

        int menor = dameMenor(indice);
        if(arbol[indice].compareTo(arbol[menor]) > 0){
            intercambia(indice, menor);
        }   
                    acomodaAbajo(menor);
    }

    /*
     * Método auxiliar que nos da el índice del hijo menor de un "vértice"
     * @param indice -índice del cual se quiere saber sobre sus hijos
     * @return int -índice del hijo menor del elemento en el índice del parámetro formal
     */
    private int dameMenor(int indice){
        if((2 * indice) + 2 >= getElementos()){
            return (2 * indice) +1;
        }
        if(arbol[(2 * indice) +1].compareTo(arbol[(2 * indice) +2]) <= 0){ //verificar si es necesario quitar el igual
            return (2 * indice) +1;
        }else{
            return (2 * indice) +2;
        }
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;

        if(!contiene(elemento))
            return;

        int indice = elemento.getIndice();
        intercambia(elemento.getIndice(), getElementos() -1);
        arbol[getElementos() -1].setIndice(-1);
        arbol[getElementos() -1] = null;
        elementos--;
        reordena(arbol[indice]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if(elemento.getIndice() < 0 || elemento.getIndice() >= getElementos())
            return false;

        return (elemento.equals(arbol[elemento.getIndice()]));
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return(getElementos() == 0);
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        for(int i = 0; i < elementos; i++){
            arbol[i].setIndice(-1);
            arbol[i] = null;
        }
        elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
       if(elemento == null || !contiene(elemento))
            return;

        acomodaAbajo(elemento.getIndice());
        acomodaArriba(elemento.getIndice()); 
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if(i < 0 || i >= getElementos())
            throw new NoSuchElementException();

        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = "";
        for (int i = 0; i < getElementos(); i++) {
            s += String.format("%s, ", get(i).toString());
        }
        return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.
            if(getElementos() != monticulo.getElementos()){
                return false;
            }else{
                for(int i = 0; i < getElementos(); i++){
                    if(!arbol[i].equals(monticulo.arbol[i])){
                        return false;
                    }
                }
                return true;
            }
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
        Lista<Adaptador<T>> l1 = new Lista<Adaptador<T>>();
        for(T elemento : coleccion){
            l1.agrega(new Adaptador<T>(elemento));
        }
        Lista<T> l2 = new Lista<T>();
        MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<Adaptador<T>>(l1);
        while(!monticulo.esVacia()){
            l2.agrega(monticulo.elimina().elemento);
        }
        return l2;
    }  
}
