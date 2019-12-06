package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            // Aquí va su código.
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if(!hasNext())
              throw new NoSuchElementException();
           
              anterior = siguiente;
              siguiente = anterior.siguiente;
              return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
            if(!hasPrevious())
                throw new NoSuchElementException();
                
                siguiente = anterior;
                anterior = anterior.anterior;
                return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return getLongitud();
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();

        Nodo auxiliar = new Nodo(elemento);
        if(esVacia()){
            cabeza = rabo = auxiliar;
        }else{
            rabo.siguiente = auxiliar;
            auxiliar.anterior = rabo;
            rabo = auxiliar;
            }
        longitud++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();

        Nodo auxiliar = new Nodo(elemento);
        if(esVacia()){
            cabeza = rabo = auxiliar;
        }else{
            cabeza.anterior = auxiliar;
            auxiliar.siguiente = cabeza;
            cabeza = auxiliar;
        }
        longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();
        
        Nodo insertable = new Nodo(elemento);
        if(i <= 0){
            agregaInicio(elemento);
        }else if(i >= getLongitud()){
            agregaFinal(elemento);
        }else{
            Nodo indice = buscaNodo(i);
            insertable.siguiente = indice;
            insertable.anterior = indice.anterior;
            indice.anterior.siguiente = insertable;
            indice.anterior = insertable;
            longitud++;
        }
    }

    /*
     * Regresa el i-ésimo nodo de la lista
     * @param i posición del nodo a encontrar
     * @throws ExcepcionIndiceInvalido si i no puede ser índice de la lista
     * @return nodo que se encuentra en la i-ésima posición de la lista
     */
    private Nodo buscaNodo(int i){
        if(i < 0 || i >= getLongitud())
            return null;

        int auxiliar = 0;
        Iterador it = (Iterador)iterator();

        if(it.siguiente.siguiente == null){
            return it.siguiente;
        }else{
            while(auxiliar < i){
                it.anterior = it.siguiente;
                it.siguiente = it.siguiente.siguiente;
                auxiliar++;
            }
            return it.siguiente;
        }
    }
    
    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(!contiene(elemento)){
            return;
        }else{
            Nodo eliminar = buscaNodo(indiceDe(elemento));
            if(eliminar.anterior == null && eliminar.siguiente == null){
                cabeza = rabo = null;
            }else if(cabeza == eliminar){
                eliminar.siguiente.anterior = null;
                cabeza = eliminar.siguiente;
            }else if(rabo == eliminar){
                eliminar.anterior.siguiente = null;
                rabo = eliminar.anterior;
            }else{
                eliminar.anterior.siguiente = eliminar.siguiente;
                eliminar.siguiente.anterior = eliminar.anterior;
            }
            longitud--;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(esVacia())
            throw new NoSuchElementException();
    
        T primero = cabeza.elemento;
        if(cabeza == rabo){
            cabeza = rabo = null;
        }else{
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        longitud--;
        return primero;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(esVacia())
            throw new NoSuchElementException();

        T ultimo = rabo.elemento;
        if(cabeza == rabo){
            cabeza = rabo = null;
        }else{
            rabo = rabo.anterior;
            rabo.siguiente = null;
        }
        longitud--;
        return ultimo;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        Iterador it = (Iterador)iterator();
        while(it.hasNext()){
            if(it.siguiente.elemento.equals(elemento))
                return true;

            it.anterior = it.siguiente;
            it.siguiente = it.siguiente.siguiente;
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> reversa = new Lista<T>();
        Iterador vuelta = (Iterador)iteradorLista();
        vuelta.end();
        while(vuelta.siguiente != cabeza){
            vuelta.siguiente = vuelta.anterior;
            vuelta.anterior = vuelta.anterior.anterior;
            reversa.agregaFinal(vuelta.siguiente.elemento);
        }
        return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        Lista <T> copia = new Lista<T>();
        copia.cabeza = cabeza;
        copia.rabo = rabo;
        copia.longitud = getLongitud();
        return copia; 
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if(esVacia())
            throw new NoSuchElementException();

        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if(esVacia())
            throw new NoSuchElementException();

        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if( i < 0 || i >= getLongitud())
            throw new ExcepcionIndiceInvalido();

        return buscaNodo(i).elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        if(!contiene(elemento)){
            return -1;
        }else{
            Iterador it = (Iterador)iterator();
            int indice = 0;
            while(!(it.siguiente.elemento.equals(elemento))){
                it.anterior = it.siguiente;
                it.siguiente = it.siguiente.siguiente;
                indice++;
            }
            return indice;
        }
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        String lista = "[";
        int contador = 0;
        Iterador it = (Iterador)iterator();
        while(contador != getLongitud()){
            lista += it.siguiente.elemento + ", ";
            it.anterior = it.siguiente;
            it.siguiente = it.siguiente.siguiente;
            contador++;
        }
        lista += "]";
        return lista.replace(", ]", "]");
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
        if(getLongitud() != lista.getLongitud())
            return false;

            Nodo esta = cabeza;
            Nodo aquella = lista.cabeza;
            while(esta != null && aquella != null){
                if(!(esta.elemento.equals(aquella.elemento)))
                    return false;

                esta = esta.siguiente;
                aquella = aquella.siguiente;
            }
            return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        return mergeSort(this, comparador);
    }

    /*
     * Método auxiliar que recibe una lista y un comparador y ordena la lista
     * @param lista1 lista a ordenar
     * @param comparador comparador utilizado para comparar los elementos de la lista
     * @return lista ordenada
     */
    private Lista <T> mergeSort(Lista <T> lista, Comparator <T> comparador){

        if(getLongitud() == 0 || getLongitud() == 1)
            return copia();

        Lista<T> lista1 = new Lista<T>();
        Lista<T> lista2 = new Lista<T>();
        int a = 0;
        int b = getLongitud();
        int medio = (a + b) / 2;

        while(a < medio){
            lista1.agrega(get(a));
            a++;
        }
        a = medio;
        while(a < b){
            lista2.agrega(get(a));
            a++;
        }
        lista1 = lista1.mergeSort(comparador);
        lista2 = lista2.mergeSort(comparador);
        return mezcla(lista1, lista2, comparador);
    }


    /*
     * Método auxiliar que recibe dos listas y regresa una lista ordenada
     * con los elementos de las dos listas
     * @param lista1 lista ordenada
     * @param lista2 lista ordenada
     * @return una lista ordenada con los elementos de las otras dos listas
     */
    private Lista<T> mezcla(Lista<T> lista1, Lista<T> lista2, Comparator <T> comparador){
        Nodo auxiliar1 = lista1.cabeza;
        Nodo auxiliar2 = lista2.cabeza;
        Lista <T> mezclada = new Lista <T>();

        while(auxiliar1 != null && auxiliar2 != null){
            if(comparador.compare(auxiliar1.elemento, auxiliar2.elemento) <= 0){
                mezclada.agrega(auxiliar1.elemento);
                auxiliar1 = auxiliar1.siguiente;
            }else{
                mezclada.agrega(auxiliar2.elemento);
                auxiliar2 = auxiliar2.siguiente;
            }
        }
        if(auxiliar1 == null)
            while(auxiliar2 != null){
                mezclada.agrega(auxiliar2.elemento);
                auxiliar2 = auxiliar2.siguiente;
            }
        if(auxiliar2 == null){
            while(auxiliar1 != null){
                mezclada.agrega(auxiliar1.elemento);
                auxiliar1 = auxiliar1.siguiente;
            }
        }
        return mezclada;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código
        for(T elem : this)
            if(comparador.compare(elem, elemento) == 0)
                return true;
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
